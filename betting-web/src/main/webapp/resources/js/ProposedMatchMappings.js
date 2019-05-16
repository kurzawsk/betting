(function ($) {
        "use strict";

        const proposedMatchMappingsTable = "#proposed-match-mappings-tbl";
        const processButton = "#process-btn";
        const refreshButton = "#refresh-btn";
        const proposedMatchMappingsRootUrl = "data/proposed-match-mappings";
        const bulkProcessUrl = "/bulk-process";
        const proposedMatchMappingsPager = "#proposed-match-mappings-pager";

        function ProposedMatchMappings() {
        }

        ProposedMatchMappings.prototype = {
            init: function () {
                showAllProposedMatchMappings();
                $(refreshButton).click(showAllProposedMatchMappings);
                $(processButton).click(processSelectedProposedMatchMappings);
            }
        };

        function processSelectedProposedMatchMappings() {

            var rows = $(proposedMatchMappingsTable).getDataIDs();
            var idsToAccept = [];
            var idsToReject = [];
            for (var a = 0; a < rows.length; a++) {
                var action = $(proposedMatchMappingsTable).jqGrid('getCell', rows[a], 'action');
                if ("Accept" === action) {
                    idsToAccept.push(rows[a]);
                } else if ("Reject" === action) {
                    idsToReject.push(rows[a]);
                }
            }

            var dto = {"idsToAccept": idsToAccept, "idsToReject": idsToReject};
            $.ajax({
                type: "POST",
                url: proposedMatchMappingsRootUrl + bulkProcessUrl,
                data: JSON.stringify(dto),
                contentType: 'application/json',
                dataType: 'application/json',
                success: showAllProposedMatchMappings,
                error: showAllProposedMatchMappings
            });
        }

        function showAllProposedMatchMappings() {
            $.jgrid.gridUnload(proposedMatchMappingsTable);
            $(proposedMatchMappingsTable).jqGrid({
                datatype: "json",
                url: proposedMatchMappingsRootUrl,
                height: "100%",
                colNames: ['Id', 'Proposed Home <br/>Team Name', 'Home Team Names', 'Home Team Similarity',
                    'Proposed Away <br/>Team Name', 'Away Team Names', 'Away Team Similarity', ' Proposed Match <br/> Start Time',
                    'Match Start Time', 'Source System', 'Action '],
                colModel: getProposedMatchMappingsColumnModel(),
                rowNum: 10, rowList: [10, 20, 50],
                pager: proposedMatchMappingsPager,
                sortname: 'id',
                viewrecords: true,
                cellEdit: true,
                cellsubmit: 'clientArray',
                editurl: 'clientArray',
                sortorder: "desc",
                loadonce: true,
                caption: "Proposed Match Mappings"
            });
        }

        function getProposedMatchMappingsColumnModel() {
            return [
                {name: 'id', index: 'id', width: 40, sorttype: "int", align: "center"},
                {name: 'proposedHomeTeamName', index: 'proposedHomeTeamName', width: 150, align: "center"},
                {name: 'homeTeamNames', index: 'homeTeamNames', width: 220, align: "center"},
                {
                    name: 'homeTeamSimilarityFactor',
                    index: 'homeTeamSimilarityFactor',
                    sorttype: "float",
                    width: 50,
                    align: "center"
                },

                {name: 'proposedAwayTeamName', index: 'proposedAwayTeamName', width: 150, align: "center"},
                {name: 'awayTeamNames', index: 'awayTeamNames', width: 220, align: "center"},
                {
                    name: 'awayTeamSimilarityFactor',
                    index: 'awayTeamSimilarityFactor',
                    sorttype: "float",
                    width: 50,
                    align: "center"
                },
                {
                    name: 'proposedStartTime',
                    index: 'proposedStartTime',
                    width: 120,
                    align: 'center',
                    formatter: "date",
                    formatoptions: {srcformat: "Y,m,d,h,i,s", newformat: "Y-m-d H:i:s"}
                },
                {
                    name: 'matchStartTime',
                    index: 'matchStartTime',
                    width: 120,
                    align: 'center',
                    formatter: "date",
                    formatoptions: {srcformat: "Y,m,d,h,i,s", newformat: "Y-m-d H:i:s"}
                },
                {name: 'sourceSystem', index: 'sourceSystem', width: 150, align: "center"},
                {
                    name: 'action',
                    index: 'action',
                    width: 125,
                    editable: true,
                    edittype: 'select',
                    align: 'center',
                    formatter: actionFormatter,
                    editoptions: {value: "NoAction:No Action;Accept:Accept;Reject:Reject"}
                }
            ];
        }

        function actionFormatter(cellvalue, options, rowObject) {
            if (!(cellvalue)) {
                return "No Action";
            } else {
                return cellvalue;
            }
        }

        window.ProposedMatchMappings = new ProposedMatchMappings();
    }(jQuery)
);