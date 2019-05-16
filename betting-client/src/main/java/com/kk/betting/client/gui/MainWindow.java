package com.kk.betting.client.gui;

import com.kk.betting.domain.MatchOdd;
import com.kk.betting.services.admin.action.AdminOperationInvokerRemote;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private final JPanel mainPanel = new JPanel();
    private final JButton findAndInsertMatchesButton = new JButton("Find And Insert Matches");
    private final JButton updateMatchOddsButton = new JButton("Update MatchOdds");
    private final JButton checkMatchResultsButton = new JButton("Check Match Results");
    private final JButton sendBetProposalButton = new JButton("Send Bet Proposal!");
    private final JTextField betProposalMatchId = new JTextField("Match Id");
    private final JTextField betProposalSource = new JTextField("ProposeAllPinnacleMatchesLogic");
    private final JComboBox expectedResultsList = new JComboBox(MatchOdd.Type.values());
    private final JTextField betProposalOdd = new JTextField("Odd");
    private final JTextField bettingProposalSourceId = new JTextField("BPS ID");
    private JButton invokeBettingProposalSourceButton = new JButton("Invoke Betting Proposal Source");
    private final JPanel reportPanel = new JPanel();
    private final JPanel betProposalPanel = new JPanel();
    private final JPanel invokeBetProposalSourcePanel = new JPanel();
    private JDatePickerImpl fromDatePicker;
    private JDatePickerImpl toDatePicker;
    private JButton createMatchStatisticsReportButton = new JButton("Create match statistics report");
    private JButton createBettorsHistoryReportButton = new JButton("Create bettors history report");


    private AdminOperationInvokerRemote adminOperationInvoker;


    public MainWindow(AdminOperationInvokerRemote adminOperationInvoker)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, IOException {
        this.adminOperationInvoker = adminOperationInvoker;
        init();
    }

    public void showMe() {
        setVisible(true);
    }

    public void init() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Betting Client");
        setSize(700, 400);
        mainPanel.add(findAndInsertMatchesButton);
        mainPanel.add(updateMatchOddsButton);
        mainPanel.add(checkMatchResultsButton);
        JDatePanelImpl fromDatePanel = new JDatePanelImpl(new UtilDateModel());
        JDatePanelImpl toDatePanel = new JDatePanelImpl(new UtilDateModel());

        fromDatePicker = new JDatePickerImpl(fromDatePanel);
        toDatePicker = new JDatePickerImpl(toDatePanel);
        reportPanel.add(fromDatePicker);
        reportPanel.add(toDatePicker);
        reportPanel.add(createMatchStatisticsReportButton);
        reportPanel.add(createBettorsHistoryReportButton);

        betProposalPanel.setLayout(new GridLayout(2, 3));

        betProposalPanel.add(betProposalMatchId);
        betProposalPanel.add(expectedResultsList);
        betProposalPanel.add(betProposalOdd);
        betProposalPanel.add(betProposalSource);
        betProposalPanel.add(sendBetProposalButton);

        invokeBetProposalSourcePanel.add(invokeBettingProposalSourceButton);
        invokeBetProposalSourcePanel.add(bettingProposalSourceId);


        mainPanel.add(reportPanel);
        mainPanel.add(betProposalPanel);
        mainPanel.add(invokeBetProposalSourcePanel);

        findAndInsertMatchesButton.addActionListener(e -> {
            try {
                adminOperationInvoker.findAndInsertNewMatches();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        checkMatchResultsButton.addActionListener(e -> {
            try {
                adminOperationInvoker.checkMatchResults();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        updateMatchOddsButton.addActionListener(e -> {
            try {
                adminOperationInvoker.updateMatchOdds();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });


        sendBetProposalButton.addActionListener(e -> {

            try {
                adminOperationInvoker.sendBettingProposal(Long.valueOf(betProposalMatchId.getText()), expectedResultsList.getSelectedItem().toString(), new BigDecimal(betProposalOdd.getText()), betProposalSource.getText());
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });

        createMatchStatisticsReportButton.addActionListener(e -> {

            try {
                String[] from = fromDatePicker.getJFormattedTextField().getText().split("-");
                String[] to = toDatePicker.getJFormattedTextField().getText().split("-");

                LocalDateTime fromDateTime = LocalDate.of(Integer.parseInt(from[0]), Integer.parseInt(from[1]), Integer.parseInt(from[2])).atStartOfDay();
                LocalDateTime toDateTime = LocalDate.of(Integer.parseInt(to[0]), Integer.parseInt(to[1]), Integer.parseInt(to[2])).atStartOfDay().withHour(23).withMinute(59).withSecond(59);

                adminOperationInvoker.prepareMatchReport(fromDateTime, toDateTime);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });

        invokeBettingProposalSourceButton.addActionListener(e -> {

            try {
                adminOperationInvoker.invokeBetOddSource(Long.valueOf(bettingProposalSourceId.getText()));
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });
        createBettorsHistoryReportButton.addActionListener(e -> {

            try {
                adminOperationInvoker.prepareAllActiveBettorsHistoryReport();
            } catch (Exception e1) {
                e1.printStackTrace();
            }


        });

        add(mainPanel, BorderLayout.CENTER);
    }
}
