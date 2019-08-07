//package finalProject;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.SecondaryLoop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import backend.Bank;
import backend.SecurityAccount;


public class SecuritiesInterface extends JPanel {
  public static String account_id;
  private static JFrame frame;
  private static final JPanel cards = new JPanel(new CardLayout());
  
  
  public SecuritiesInterface() {
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
  } 
  
  public SecuritiesInterface(String option) {
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    JLabel name = new JLabel(option);
    name.setAlignmentX(Component.CENTER_ALIGNMENT);
    name.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(name);
  }
  
  public static void createSecuritiesOptions() {
    frame = new JFrame();
    frame.setBounds(100, 100, 450, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    createMainPage();
//    createMarketBuyPage();
//    createViewSellPage();
//    createHistoryPage();
    
    frame.add(cards, BorderLayout.CENTER);
    JButton backButton = new JButton("Go Back");
    backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    backButton.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, "Main");
      }
    });
    frame.add(backButton, BorderLayout.SOUTH);
    frame.setVisible(true);
  }
  
  private static void createMainPage() {
    SecuritiesInterface welcomePage = new SecuritiesInterface("Welcome to your trading portfolio!");
    JLabel questionLbl = new JLabel("What would you like to do?");
    questionLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
    welcomePage.add(questionLbl);
    
    DefaultListModel<String> model = new DefaultListModel<>();
    JList<String> options = new JList<String>(model);
    
    model.addElement("View current market/Purchase investments");
    model.addElement("View/Sell my investments");
    model.addElement("View my trading history");
    
    
    options.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          String desiredAction = (String) options.getSelectedValue();
          System.out.println(desiredAction);
          CardLayout cl = (CardLayout)(cards.getLayout());
          switch (desiredAction) {
            case "View current market/Purchase investments":
            	createMarketBuyPage();
              cl.show(cards, "Market/Buy");
              break;
            case "View/Sell my investments":
            	createViewSellPage();
              cl.show(cards, "View/Sell");
              break;
            case "View my trading history":
            	createHistoryPage();
              cl.show(cards, "History");
              break;
          }
        }
      }
    });

    welcomePage.add(options);
    
    
    cards.add(welcomePage, "Main");
  }
  
  private static void createMarketBuyPage() {
    SecuritiesInterface marketBuyPage = new SecuritiesInterface();
    JTabbedPane marketOrBuy = new JTabbedPane();
    
    JPanel marketPage = new JPanel();
    String[] columnNames = {"Stock ID", "Company Name", "Ticker Symbol", "Current Price (USD)", "Percentage Change"};
    Object[][] data = new Object[10][5];
    data[0][0] = "Test Stock";
    data[0][1] = "TST";
    data[0][2] = 34;
    data[0][3] = "0.0%";
    
    int index = 1;
    //String stock_info = (new Bank()).getInvestmentInfo();
    String stock_info = MainPage.bank.getInvestmentInfo();
    for(String each_stock: stock_info.split("\\|")) {
    	if(each_stock.contains("Bond"))
    		break;
    	
    	System.out.println(each_stock);
    	String info[] = each_stock.split("-");
    	
    	data[index][0] = info[0];
    	data[index][1] = info[1];
    	data[index][2] = info[2];
    	data[index][3] = info[3];
    	data[index++][4] = "TODO";
    }
    
    
    JTable marketTable = new JTable(data, columnNames);
    DefaultTableCellRenderer marketRenderer = new DefaultTableCellRenderer();
    marketRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    JScrollPane market = new JScrollPane(marketTable);
    market.setPreferredSize(new Dimension(450, 110));
    market.setAlignmentX(Component.CENTER_ALIGNMENT);
    marketPage.add(market);
    marketOrBuy.addTab("Current Market", marketPage);
    
    JPanel buyPage = new JPanel();
    JPanel stockPane = new JPanel();
    stockPane.setLayout(new BoxLayout(stockPane, BoxLayout.LINE_AXIS));
    JLabel purchaseStocks = new JLabel("Purchase ");
    stockPane.add(purchaseStocks);
    JTextField numShares = new JTextField("# of shares");
    stockPane.add(numShares);
    JLabel ofMessage = new JLabel(" of ");
    stockPane.add(ofMessage);
    JTextField stockType = new JTextField("Ticker Symbol");
    stockPane.add(stockType);
    JButton confirmStocks = new JButton("Buy stocks");
    stockPane.add(confirmStocks);
    buyPage.add(stockPane);
    confirmStocks.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			Integer num = Integer.valueOf(numShares.getText());
			String stock_id = stockType.getText();
			
			if(MainPage.bank.buyStock(stock_id, num, 0.0, account_id)) {
				System.out.println("Succeed");

				new UserMainPage();
				frame.dispose();
			}
			else {
				System.out.println("Failed");
			}
		}
	});
    
    
    
    JPanel bondPane = new JPanel();
    bondPane.setLayout(new BoxLayout(bondPane, BoxLayout.LINE_AXIS));
    JLabel purchaseBonds = new JLabel("Purchase ");
    bondPane.add(purchaseBonds);
    JTextField numBonds = new JTextField("# of bonds");
    bondPane.add(numBonds);
    String[] bondTypes = {"week long", "month long", "3-month long"};
    JComboBox<String> bondMenu = new JComboBox<String>(bondTypes);
    bondPane.add(bondMenu);
    JLabel bondMessage = new JLabel(" bonds");
    bondPane.add(bondMessage);
    JButton confirmBonds = new JButton("Buy bonds");
    bondPane.add(confirmBonds);
    buyPage.add(bondPane);
    marketOrBuy.addTab("Buy Investments", buyPage);
    
    marketBuyPage.add(marketOrBuy);
    cards.add(marketBuyPage, "Market/Buy");
  }
  
  private static void createViewSellPage() {
    SecuritiesInterface viewSellPage = new SecuritiesInterface();
    JTabbedPane viewOrSell = new JTabbedPane();
    JPanel stockTab = new JPanel();
    stockTab.setLayout(new BoxLayout(stockTab, BoxLayout.PAGE_AXIS));
    JLabel stockLabel = new JLabel("Stocks");
    stockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    stockTab.add(stockLabel);
    String[] stockColumnNames = {"Stock ID", "Company Name", "Ticker Symbol", "Current Price (USD)", "Owned Shares", "Profit/Loss per Share (USD)"};
    Object[][] stockData = new Object[4][6];
    stockData[0][0] = "Test Stock";
    stockData[0][1] = "TST";
    stockData[0][2] = "34";
    stockData[0][3] = "0.0";
    
    int index = 0;
    SecurityAccount account = (SecurityAccount) UserMainPage.user.getAccount(account_id);
    String investment_info = account.getInvestment();
    for(String info : investment_info.split("\\|")) {
    	System.out.println(info);
    	if(info.contains("Bond"))
    		break;
    	
    	String col[] = info.split("-");
    	stockData[index][0] = col[0];
        stockData[index][1] = col[1];
        stockData[index][2] = col[2];
        stockData[index][3] = col[3];
        stockData[index][4] = col[4];
        stockData[index++][5] = "TODO";
    }
    
    JTable stockTable = new JTable(stockData, stockColumnNames);
    JScrollPane stocks = new JScrollPane(stockTable);
    stocks.setAlignmentX(Component.CENTER_ALIGNMENT);
    stockTab.add(stocks);
    viewOrSell.addTab("My Stocks", new JScrollPane(stockTab));
    
    JPanel bondTab = new JPanel();
    bondTab.setLayout(new BoxLayout(bondTab, BoxLayout.PAGE_AXIS));
    JLabel bondLabel = new JLabel("Bonds");
    bondLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    bondTab.add(bondLabel);
    String[] bondColumnNames = {"Bond Type", "Interest Rate", "Date Purchased (MM/DD/YY)", "Days Until Maturity"};
    Object[][] bondData = new Object[4][4];
    bondData[0][0] = "Weekly";
    bondData[0][1] = 0.01;
    bondData[0][2] = "08/06/19";
    bondData[0][3] = "7";
    JTable bondTable = new JTable(bondData, bondColumnNames);
    JScrollPane bonds = new JScrollPane(bondTable);
    stocks.setAlignmentX(Component.CENTER_ALIGNMENT);
    bondTab.add(bonds);
    viewOrSell.addTab("My Bonds", new JScrollPane(bondTab));
    viewSellPage.add(viewOrSell);
    
    JPanel sellTab = new JPanel();
    JPanel stockPane = new JPanel();
    stockPane.setLayout(new BoxLayout(stockPane, BoxLayout.LINE_AXIS));
    JLabel sellStocks = new JLabel("Sell ");
    stockPane.add(sellStocks);
    JTextField numShares = new JTextField("# of shares");
    stockPane.add(numShares);
    JLabel ofMessage = new JLabel(" of ");
    stockPane.add(ofMessage);
    Object[] stockTypes = new String[stockTable.getModel().getRowCount()];
    for (int i = 0; i < stockTypes.length; i++) {
      stockTypes[i] = stockTable.getModel().getValueAt(i,1);
    }
    JComboBox<Object> stockMenu = new JComboBox<Object>(stockTypes);
    stockPane.add(stockMenu);
    JButton confirmStocks = new JButton("Sell stocks");
    stockPane.add(confirmStocks);
    sellTab.add(stockPane);
    confirmStocks.addMouseListener(new MouseAdapter() {
    	@Override
		public void mouseClicked(MouseEvent arg0) {
			Integer num = Integer.valueOf(numShares.getText());
			//String stock_id = stockMenu.get;
			
			if(MainPage.bank.buyStock(stock_id, num, 0.0, account_id)) {
				System.out.println("Succeed");

				new UserMainPage();
				frame.dispose();
			}
			else {
				System.out.println("Failed");
			}
		}
	});
    
    
    JPanel bondPane = new JPanel();
    bondPane.setLayout(new BoxLayout(bondPane, BoxLayout.LINE_AXIS));
    JLabel sellBonds = new JLabel("Sell ");
    bondPane.add(sellBonds);
    JTextField numBonds = new JTextField("# of bonds");
    bondPane.add(numBonds);
    String[] bondTypes = {"week long", "month long", "3-month long"};
    JComboBox<String> bondMenu = new JComboBox<String>(bondTypes);
    bondPane.add(bondMenu);
    JLabel bondMessage = new JLabel(" bonds");
    bondPane.add(bondMessage);
    JButton confirmBonds = new JButton("Sell bonds");
    bondPane.add(confirmBonds);
    sellTab.add(bondPane);
    
    viewOrSell.addTab("Sell Investments", sellTab);
    cards.add(viewSellPage, "View/Sell");
  }
  
  private static void createHistoryPage() {
    SecuritiesInterface historyPage = new SecuritiesInterface("Transaction History");
    cards.add(historyPage, "History");
  }
  
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {

      @Override
      public void run() {
        createSecuritiesOptions();
      }
  });
  }
}
