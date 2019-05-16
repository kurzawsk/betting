(function ($) {
        "use strict";

        const bettingEventsTable = "#betting-events-tbl";
        const bettorsTable = "#bettors-tbl";
        const showAllButton = "#refresh-btn";
        const bettorsRootUrl = "data/bettors";
        const bettingEventsUrl = "/betting-events";
        const bettingEventsPager = "#betting-events-pager";
        const bettorsPager = "#bettors-pager";
        const selectedBettingEventTypes = "#selected-betting-event-types";
        const bettingEventTypesRadioName = "betting-event-types";
        const narrowPeriod = "#narrow-period";
        const fromDate = "#from-date";
        const toDate = "#to-date";
        const dateFormat = "yy-mm-dd";
        const fromQP = "from";
        const toQP = "to";
        const showTypesQP = "showTypes";
        const showResourcesChartBtn = "#show-resources-chart-btn";
        const resourcesChartDiv = "bettor-resource-chart-div";
        const resourcesChartContainer = "#bettor-resource-chart-div-container";

        function Bettors() {

        }

        Bettors.prototype = {
            init: function () {
                showAllBettors();
                $(showAllButton).click(showAllBettors);
                $(narrowPeriod).click(function () {
                    if ($(this).is(':checked')) {
                        $(fromDate).prop("disabled", false);
                        $(toDate).prop("disabled", false);
                        refreshBettingHistoryIfBettorSelected();
                    } else {
                        $(fromDate).prop("disabled", true);
                        $(toDate).prop("disabled", true);
                        $(fromDate).val('');
                        $(toDate).val('');
                    }
                });

                $('input:radio[name=' + bettingEventTypesRadioName + ']').change(function () {
                    if ($(this).is(':checked')) {
                        $(selectedBettingEventTypes).val($(this).val());
                        refreshBettingHistoryIfBettorSelected();
                    }
                });

                $(fromDate).datepicker({
                    dateFormat: dateFormat,
                    defaultDate: "+1w",
                    changeMonth: true,
                    numberOfMonths: 2
                }).on("change", function () {
                    $(toDate).datepicker("option", "minDate", getDate(this));
                    refreshBettingHistoryIfBettorSelected();
                });

                $(toDate).datepicker({
                    dateFormat: dateFormat,
                    defaultDate: "+1w",
                    changeMonth: true,
                    numberOfMonths: 2
                }).on("change", function () {
                    $(fromDate).datepicker("option", "maxDate", getDate(this));
                    refreshBettingHistoryIfBettorSelected();
                });

                $(resourcesChartContainer).dialog({
                    width: 1000,
                    height: 700,
                    modal: true,
                    autoOpen: false,
                    title: "Available resources history",
                    open: function () {
                        var bettorId = $(bettorsTable).jqGrid('getGridParam', 'selrow');
                        if (bettorId != null) {
                            var chart = drawBettorResourcesGraph(bettorId);
                            setTimeout(function () {
                                chart.render();
                            }, 2000);
                        }
                    }
                });

                $(showResourcesChartBtn).on('click', function (e) {
                    var bettorId = $(bettorsTable).jqGrid('getGridParam', 'selrow');
                    if (bettorId != null) {
                        $(resourcesChartContainer).dialog("open");
                    }
                });
            }
        };

        function drawBettorResourcesGraph(bettorId) {
            var dataPoints = [];
            $.getJSON(bettorsRootUrl + "/" + bettorId + bettingEventsUrl + "?" + showTypesQP + "=" + $(selectedBettingEventTypes).val() + "&" + fromQP + "=" + $(fromDate).val() + "&" + toQP + "=" + $(toDate).val(), function (data) {
                $.each(data, function (key, value) {
                    dataPoints.push({
                        x: new Date(value.eventTime[0], parseInt(value.eventTime[1]) - 1, value.eventTime[2], value.eventTime[3], value.eventTime[4], value.eventTime[5], 0),
                        y: parseFloat(value.resourcesAvailableAfterEvent)
                    });
                });
            });

            dataPoints.reverse();
            var data = [];
            var dataSeries = {
                type: "line",
                lineThickness: 2,
                markerSize: 4,
                markerType: "circle",
                color: "#4B0082"
            };

            dataSeries.dataPoints = dataPoints;
            data.push(dataSeries);

            var options = {
                zoomEnabled: true,
                data: data
            };
            return new CanvasJS.Chart(resourcesChartDiv, options);
        }

        function refreshBettingHistoryIfBettorSelected() {
            var bettorId = $(bettorsTable).jqGrid('getGridParam', 'selrow');
            if (bettorId != null) {
                showBettingEventHistory(bettorId);
            }
        }

        function getDate(element) {
            var date;
            try {
                date = $.datepicker.parseDate(dateFormat, element.value);
            } catch (error) {
                date = null;
            }

            return date;
        }

        function showAllBettors() {
            $.jgrid.gridUnload(bettingEventsTable);
            $.jgrid.gridUnload(bettorsTable);
            $(bettorsTable).jqGrid({
                datatype: "json",
                url: bettorsRootUrl,
                height: "100%",
                colNames: ['Id', 'Description', 'Resources', 'Avg Res. Since <br> Last Algn.', 'Total Profit', 'Bookmaker', 'Parameters', 'Active'],
                colModel: getBettorsColumnModel(),
                multiselect: false,
                rowNum: 10, rowList: [10, 20, 50],
                pager: bettorsPager,
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                loadonce: true,
                caption: "Bettors",
                onSelectRow: function (bettorId) {
                    $(showResourcesChartBtn).attr("disabled", false);
                    showBettingEventHistory(bettorId);
                },
                ondblClickRow: function (bettorId) {
                    var parameters = $(bettorsTable).jqGrid('getCell', bettorId, 'parameters');
                    alert("parameters = " + parameters);
                }
            });
        }

        function showBettingEventHistory(bettorId) {
            $.jgrid.gridUnload(bettingEventsTable);
            $(bettingEventsTable).jqGrid({
                datatype: "json",
                url: bettorsRootUrl + "/" + bettorId + bettingEventsUrl + "?" + showTypesQP + "=" + $(selectedBettingEventTypes).val() + "&" + fromQP + "=" + $(fromDate).val() + "&" + toQP + "=" + $(toDate).val(),
                colNames: ['Id', 'Event Type', 'Event Time', 'Match', 'Match Start Time', 'Resources<br/>Before', 'Resources<br/>After', 'Expected | Virtual<br/>Result', 'Odd', 'Proposal Source'],
                colModel: getBettingEventsColumnModel(),
                multiselect: false,
                height: "100%",
                rowNum: 10, rowList: [10, 20, 50],
                pager: bettingEventsPager,
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                loadonce: true,
                caption: "Betting Events",
                rowattr: function (rd) {
                    if (rd.eventType === "Match bet") {
                        return {"class": "matchBetRowClass"};
                    } else if (rd.eventType === "Match withdrawn") {
                        return {"class": "matchWithDrawnRowClass"};
                    } else if (rd.eventType === "Bet successful") {
                        return {"class": "betSuccessfulRowClass"};
                    } else if (rd.eventType === "Bet unsuccessful") {
                        return {"class": "betunsuccessfulRowClass"};
                    } else if (rd.eventType === "Money added") {
                        return {"class": "moneyAddedRowClass"};
                    } else if (rd.eventType === "Money withdrawn") {
                        return {"class": "moneyWithdrawnRowClass"};
                    }
                }
            });
        }

        function getBettorsColumnModel() {
            return [
                {name: 'id', index: 'id', width: 60, sorttype: "int", align: "center"},
                {name: 'description', index: 'description', width: 150, align: "center"},
                {
                    name: 'availableResources',
                    index: 'availableResources',
                    width: 60,
                    sorttype: "float",
                    align: "center"
                },
                {
                    name: 'avgResourcesSinceLastAlignment',
                    index: 'avgResourcesSinceLastAlignment',
                    width: 80,
                    sorttype: "float",
                    align: "center"
                },
                {
                    name: 'totalProfit',
                    index: 'totalProfit',
                    width: 60,
                    sorttype: "float",
                    align: "center"
                },
                {name: 'bookmaker', index: 'bookmaker', width: 100, align: "center"},
                {name: 'parameters', index: 'parameters', classes: "no-wrap-col", width: 600},
                {name: 'active', formatter: "checkbox", width: 40, formatoptions: {disabled: true, editable: false}}
            ];
        }

        function getBettingEventsColumnModel() {
            return [
                {name: 'id', index: 'id', width: 60, sorttype: "int", align: "center"},
                {name: 'eventType', index: 'eventType', width: 100, align: "center"},
                {
                    name: 'eventTime',
                    index: 'eventTime',
                    width: 120,
                    align: 'center',
                    formatter: "date",
                    formatoptions: {srcformat: "Y,m,d,h,i,s", newformat: "Y-m-d H:i:s"}
                },
                {name: 'match', index: 'match', width: 200, align: "center"},
                {
                    name: 'matchStartTime',
                    index: 'matchStartTime',
                    width: 120,
                    align: 'center',
                    formatter: "date",
                    formatoptions: {srcformat: "Y,m,d,h,i,s", newformat: "Y-m-d H:i:s"}
                },
                {
                    name: 'resourcesAvailableBeforeEvent',
                    index: 'resourcesAvailableBeforeEvent',
                    sorttype: "float",
                    width: 80,
                    align: "center"
                },
                {
                    name: 'resourcesAvailableAfterEvent',
                    index: 'resourcesAvailableAfterEvent',
                    sorttype: "float",
                    width: 80,
                    align: "center"
                },
                {name: 'expectedMatchResult', index: 'expectedMatchResult', width: 150, align: "center"},
                {name: 'betOdd', index: 'betOdd', width: 50, sorttype: "float", align: "center"},
                {name: 'proposalSource', index: 'proposalSource', width: 300, align: "center"}
            ];
        }

        window.Bettors = new Bettors();
    }(jQuery)
);