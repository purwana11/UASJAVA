package uasjava;

import java.io.File;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import net.sf.jasperreports.engine.JasperCompileManager; // <- awas salah
import net.sf.jasperreports.engine.JasperFillManager;//<- awas salah
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author ASUS
 */
public class mainFormController implements Initializable{
    
    @FXML
    private Button customers_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button inventort_btn;

    @FXML
    private Button inventory_addBtn;

    @FXML
    private Button inventory_clearBtn;

    @FXML
    private TableColumn<?, ?> inventory_coi_date;

    @FXML
    private Button inventory_coi_importBtn;

    @FXML
    private TableColumn<productData, String> inventory_coi_price;

    @FXML
    private TableColumn<productData, String> inventory_coi_productID;

    @FXML
    private TableColumn<productData, String> inventory_coi_productName;

    @FXML
    private TableColumn<productData, String> inventory_coi_status;

    @FXML
    private TableColumn<productData, String> inventory_coi_stock;

    @FXML
    private TableColumn<productData, String> inventory_coi_type;

    @FXML
    private Button inventory_deleteBtn;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private ImageView inventory_imageView;

    @FXML
    private TableView<productData> inventory_tableView;

    @FXML
    private Button inventory_upadeteBtn;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button menu_btn;

    @FXML
    private Label username;
    
    @FXML
    private TextField inventory_price;

    @FXML
    private TextField inventory_productID;

    @FXML
    private TextField inventory_productName;
    
    @FXML
    private ComboBox<?> inventory_status;

    @FXML
    private TextField inventory_stock;
    
    @FXML
    private ComboBox<?> inventory_type;
    
    @FXML
    private Button menu_ReceiptBtn;
     
    @FXML
    private TextField menu_amount;

    @FXML
    private Label menu_change;

    @FXML
    private TableColumn<productData, String> menu_col_prce;

    @FXML
    private TableColumn<productData, String> menu_col_productName;

    @FXML
    private TableColumn<productData, String> menu_col_quantity;
    
    @FXML
    private ScrollPane menu_scrolPane;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private Button menu_payBtn;

    @FXML
    private Button menu_removeBtn;

    @FXML
    private TableView<productData> menu_tableView;

    @FXML
    private Label menu_total;
    
    @FXML
    private AnchorPane dashboard_form;
    
    @FXML
    private TableColumn<customersData, String> customer_col_cashier;

    @FXML
    private TableColumn<customersData, String> customer_col_customer_ID;

    @FXML
    private TableColumn<customersData, String> customer_col_date;

    @FXML
    private TableColumn<customersData, String> customer_col_total;

    @FXML
    private AnchorPane customer_form;
    
    @FXML
    private TableView<customersData> cutomer_tableView;
    
    @FXML
    private AreaChart<?, ?> dashboard_incomeChart;
    
    @FXML
    private BarChart<?, ?> dashboard_CustomerChart;

    @FXML
    private Label dashboard_NC;

    @FXML
    private Label dashboard_NSP;

    @FXML
    private Label dashboard_TI;

    @FXML
    private Label dashboard_Totall;
    
    
  
    
    
    private Alert alert;
       
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private Image image;
    
    private ObservableList<productData> cardListData = FXCollections.observableArrayList();
    
    public void dashboardDisplayNC() {
        
        String sql = "SELECT COUNT(id) FROM receipt";
        connect = database.connetDB();
        
        try {
            int nc = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) {
                nc = result.getInt("COUNT(id)");
            }
            dashboard_NC.setText(String.valueOf(nc));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
     public void dashboardDisplayTI() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM receipt WHERE date = '"
                + sqlDate + "'";

        connect = database.connetDB();

        try {
            double ti = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }

            dashboard_TI.setText("Rp." + ti);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public void dashboardTotall() {
        String sql = "SELECT SUM(total) FROM receipt";

        connect = database.connetDB();

        try {
            float ti = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                ti = result.getFloat("SUM(total)");
            }
            dashboard_Totall.setText("Rp." + ti);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public void dashboardNSP() {

        String sql = "SELECT COUNT(quantity) FROM customer";

        connect = database.connetDB();

        try {
            int q = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                q = result.getInt("COUNT(quantity)");
            }
            dashboard_NSP.setText(String.valueOf(q));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
     public void dashboardIncomeChart() {
        dashboard_incomeChart.getData().clear();

        String sql = "SELECT date, SUM(total) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        connect = database.connetDB();
        XYChart.Series chart = new XYChart.Series();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getFloat(2)));
            }

            dashboard_incomeChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
     public void dashboardCustomerChart() {
        dashboard_CustomerChart.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        connect = database.connetDB();
        XYChart.Series chart = new XYChart.Series();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
            }

            dashboard_CustomerChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public void inventoryAddBtn(){
        
        if(inventory_productID.getText().isEmpty()
                || inventory_productName.getText().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty()
                || inventory_status.getSelectionModel().getSelectedItem() == null
                || data.path == null){        
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fileds");
            alert.showAndWait();
        }else {
            String checkProdID = "SELECT prod_id FROM product WHERE prod_id = '"
                    + inventory_productID.getText() + "'";
            
            connect = database.connetDB();
            
            try{
                statement = connect.createStatement();
                result = statement.executeQuery(checkProdID);
                
                if(result.next()){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(inventory_productID.getText()+"is already taken");
                    alert.showAndWait();
                }else{
                    String insertData = "INSERT INTO product"
                            +"(prod_id, prod_name, type, stock, price, status, image, date)"
                            +"VALUES(?,?,?,?,?,?,?,?)";                 
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, inventory_productID.getText());
                    prepare.setString(2, inventory_productName.getText());
                    prepare.setString(3, (String)inventory_type.getSelectionModel().getSelectedItem());
                    prepare.setString(4, inventory_stock.getText());
                    prepare.setString(5, inventory_price.getText());
                    prepare.setString(6, (String)inventory_status.getSelectionModel().getSelectedItem());                    
                    String path = data.path;
                    path = path.replace("\\", "\\\\");                   
                    prepare.setString(7, path);                   
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());                    
                    prepare.setString(8,String.valueOf(sqlDate));
                    prepare.executeUpdate();   
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succesfully added");
                    alert.showAndWait(); 
                    inventoryShowData();
                    inventoryClearBtn();                         
                }          
            }catch(Exception e){
                e.printStackTrace();
            }   
        }          
    }

    public void inventoryUpdateBtn() {

    if (inventory_productID.getText().isEmpty()
            || inventory_productName.getText().isEmpty()
            || inventory_type.getSelectionModel().getSelectedItem() == null
            || inventory_stock.getText().isEmpty()
            || inventory_price.getText().isEmpty()
            || inventory_status.getSelectionModel().getSelectedItem() == null
            || data.path == null || data.id == 0) {

        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Please fill all blank fields");
        alert.showAndWait();
    } else {

        String updateData = "UPDATE product SET "
                + "prod_id = ?, prod_name = ?, type = ?, stock = ?, price = ?, status = ?, image = ?, date = ? "
                + "WHERE id = ?";

        connect = database.connetDB();

        try {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE Product ID: " + inventory_productID.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                prepare = connect.prepareStatement(updateData);
                prepare.setString(1, inventory_productID.getText());
                prepare.setString(2, inventory_productName.getText());
                prepare.setString(3, (String)inventory_type.getSelectionModel().getSelectedItem());
                prepare.setString(4, inventory_stock.getText());
                prepare.setString(5, inventory_price.getText());
                prepare.setString(6, (String)inventory_status.getSelectionModel().getSelectedItem());
                prepare.setString(7, data.path);
                prepare.setString(8, data.date);
                prepare.setInt(9, data.id);

                int rowsAffected = prepare.executeUpdate();

                if (rowsAffected > 0) {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    inventoryShowData();
                    inventoryClearBtn();
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to update. Please check your input.");
                    alert.showAndWait();
                }
            } else {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Tangani kesalahan SQL
        } finally {
            try {
                if (prepare != null) {
                    prepare.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

    public void inventoryDeleteBtn(){
    
    if (data.id == 0) {

        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Please fill all blank fields");
        alert.showAndWait();
    } else {
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to DELETE Product ID: " + inventory_productID.getText()+ "?");
        Optional<ButtonType> option = alert.showAndWait();
        
        if(option.get().equals(ButtonType.OK)){
            String deleteData = "DELETE FROM product WHERE id = " + data.id;
            try{
                prepare = connect.prepareStatement(deleteData);
                prepare.executeUpdate();
                
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Deleted!");
                alert.showAndWait();
                inventoryShowData();
                inventoryClearBtn();
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Canceled");
            alert.showAndWait();
        } 
    }   
}
  
    public void inventoryClearBtn(){
        inventory_productID.setText("");
        inventory_productName.setText("");
        inventory_type.getSelectionModel().clearSelection();
        inventory_stock.setText("");
        inventory_price.setText("");
        inventory_status.getSelectionModel().clearSelection();
        data.path = "";
        data.id = 0;
        inventory_imageView.setImage(null);
    }

    public void inventoryImportBtn(){
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("open image file", "*PNG","*JPG"));
        
        File file = openFile.showOpenDialog(main_form.getScene().getWindow());
        
        if(file != null){
            data.path = file.getAbsolutePath();
            image = new Image (file.toURI().toString(),163,157,false,true);           
            inventory_imageView.setImage(image);       
        }
    }

    public ObservableList<productData> inventoryDataList(){
        
        ObservableList<productData>listData = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM product";
        
        connect = database.connetDB();
        
        try{
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            productData prodData;
            while(result.next()){
                prodData = new productData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date"));

                listData.add(prodData);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<productData> inventoryListData;
    public void inventoryShowData(){
        inventoryListData = inventoryDataList();    
        inventory_coi_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_coi_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_coi_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_coi_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_coi_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_coi_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventory_coi_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        inventory_tableView.setItems(inventoryListData);
    }
    
    public void inventorySelectData(){
        
        productData prodData = inventory_tableView.getSelectionModel().getSelectedItem();
        int num = inventory_tableView.getSelectionModel().getSelectedIndex();
        
        if((num - 1) < -1) return;
        
        inventory_productID.setText(prodData.getProductId());
        inventory_productName.setText(prodData.getProductName());
        inventory_stock.setText(String.valueOf(prodData.getStock()));
        inventory_price.setText(String.valueOf(prodData.getPrice()));
        data.path = prodData.getImage(); 
        String path = "File:" + prodData.getImage();
        data.date = String.valueOf(prodData.getDate());
        data.id = prodData.getId();
        
        image = new Image(path, 163, 157, false, true);
        inventory_imageView.setImage(image);
        
    }
    
    
    private String[] typeList = {"Meals", "Drinks"};
    public void inventoryTypeList(){
        
        List<String> typeL = new ArrayList<>();
        
        for (String data: typeList){
            typeL.add(data);        
        }
        
        ObservableList listData = FXCollections.observableArrayList(typeL);
        inventory_type.setItems(listData);      
    }
    
    private String[] statusList = {"available", "unavailable"};
    public void inventoryStatusList(){
        List<String> statusL = new ArrayList<>();
        
        for (String data: statusList){
            statusL.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(statusL);
        inventory_status.setItems(listData);
    }
 
     public ObservableList<productData> menuGetData() {
        
        String sql = "SELECT * FROM product";
        
        ObservableList<productData> listData = FXCollections.observableArrayList();
        connect = database.connetDB();
        
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            productData prod;
            
            while (result.next()) {
                prod = new productData(result.getInt("id"),
                       result.getString("prod_id"),
                        result.getString("prod_name"),  
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date")); 
                listData.add(prod);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listData;
    }
     
     public void menuDisplayCard() {
        
        cardListData.clear();
        cardListData.addAll(menuGetData());
        
        int row = 0;
        int column = 0;
        
        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();
        
        for (int q = 0; q < cardListData.size(); q++) {
            
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("cardProduct.fxml"));
                AnchorPane pane = load.load();
                cardProductController cardC = load.getController();
                cardC.setData(cardListData.get(q));
                
                if (column == 3) {
                    column = 0;
                    row += 1;
                }
                
                menu_gridPane.add(pane, column++, row);          
                GridPane.setMargin(pane, new Insets(10));
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ObservableList<productData> menuGetOrder() {
        customerID();
        ObservableList<productData> listData = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM customer WHERE customer_id = "+ cID;
        
        connect = database.connetDB();
        
        try{
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            productData prod;
            
            if (result.next()) {
                do {
                    prod = new productData(result.getInt("id"),
                            result.getString("prod_id"),
                            result.getString("prod_name"),
                            result.getString("type"),
                            result.getInt("quantity"),
                            result.getDouble("price"),
                            result.getString("image"),
                            result.getDate("date"));
                    listData.add(prod);
                } while (result.next());
            }
       
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listData;
    }
    
    private ObservableList<productData> menuOrderListData;
    public void menuShowOrderData(){
        menuOrderListData = menuGetOrder();
        
        menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_col_prce.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        menu_tableView.setItems(menuOrderListData);
    }
    private int getid;
    public void menuSelectOrder(){
        productData prod = menu_tableView.getSelectionModel().getSelectedItem();
        int num = menu_tableView.getSelectionModel().getSelectedIndex();
        
        if((num -1)< -1) return;
        getid = prod.getId();
    }

    private double totalP;
    public void menuGetTotal(){
        customerID();
        String total = "SELECT SUM(price) FROM customer WHERE customer_id = " + cID;
        
        connect = database.connetDB();
        
        try{
            
            prepare = connect.prepareStatement(total);
            result = prepare.executeQuery();
            
            if(result.next()){
                totalP = result.getDouble("SUM(price)");
            }
          
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menuDisplayTotal(){
        menuGetTotal();
        menu_total.setText("Rp." + totalP);
    }
    
    private double amount;
    private double change;
    
    public void menuAmount(){
        menuGetTotal();
        if(menu_amount.getText().isEmpty() || totalP == 0){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid :3");
            alert.showAndWait();
        }else{
            amount = Double.parseDouble(menu_amount.getText());
            if(amount < totalP){
                menu_amount.setText("");
            }else{
                change = (amount - totalP);
                menu_change.setText("Rp."+change);
            }
        }       
    }
    
    public void menuPayBtn(){
        
        if(totalP == 0){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please choose your order first!");
            alert.showAndWait();
        }else{
            menuGetTotal();
            String insertPay = "INSERT INTO receipt (customer_id, total, date, em_username)"
                    + "VALUES(?,?,?,?)";           
            connect = database.connetDB();
            
            try{
                
                if(amount == 0){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Something wrong :3");
                    alert.showAndWait();
                }else{
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("are you sure?");
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {
                        customerID();
                        menuGetTotal();
                        prepare = connect.prepareStatement(insertPay);
                        prepare.setString(1, String.valueOf(cID));
                        prepare.setString(2, String.valueOf(totalP));            
                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());                  
                        prepare.setString(3, String.valueOf(sqlDate));
                        prepare.setString(4, data.username);                       
                        prepare.executeUpdate();            
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successful.");
                        alert.showAndWait();
                        menuShowOrderData();                   
                    } else {
                        alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Cancelled.");
                        alert.showAndWait();
                    }
                }               
            }catch(Exception e){
                e.printStackTrace();
            }
        }     
    } 
   
    public void menuRemoveBtn() {
        
        if (getid == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the order you want to remove");
            alert.showAndWait();
        } else {
            String deleteData = "DELETE FROM customer WHERE id = " + getid;
            connect = database.connetDB();
            try {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this order?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                }
                
                menuShowOrderData();
            } catch (Exception e) {
                e.printStackTrace();
            }            
        }
    }

     public void menuReceiptBtn() {
        
        if (totalP == 0 || menu_amount.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Please order first");
            alert.showAndWait();
        } else {
            HashMap map = new HashMap();
            map.put("getReceipt", (cID - 1));
            
            try {
                
                JasperDesign jDesign = JRXmlLoader.load("D:\\UAS\\uasJavaSebenarnya\\UASJAVA\\src\\uasjava\\report.jrxml");
                JasperReport jReport = JasperCompileManager.compileReport(jDesign);
                JasperPrint jPrint = JasperFillManager.fillReport(jReport, map, connect);            
                JasperViewer.viewReport(jPrint, false);            
                menuRestart();     
            } catch (Exception e) {
                e.printStackTrace();
            }         
        }        
    }
 
    public void menuRestart(){
        totalP = 0;
        change = 0;
        amount = 0;
        menu_total.setText("0.0");
        menu_amount.setText("");
        menu_change.setText("0.0");
    }
    
    private int cID;   
    
    public void customerID() {

        String sql = "SELECT MAX(customer_id) FROM customer";
        connect = database.connetDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                cID = result.getInt("MAX(customer_id)");
            }

            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();
            int checkID = 0;
            if (result.next()) {
                checkID = result.getInt("MAX(customer_id)");
            }

            if (cID == 0) {
                cID += 1;
            } else if (cID == checkID) {
                cID += 1;
            }

            data.cID = cID;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<customersData> customersDataList(){
        
        ObservableList<customersData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM receipt";
        connect = database.connetDB();
        
        try {
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            customersData cData;
            
            while (result.next()) {
                cData = new customersData(result.getInt("id"),
                        result.getInt("customer_id"),
                        result.getDouble("total"),
                        result.getDate("date"),
                        result.getString("em_username"));
                
                listData.add(cData);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
        
    }
    
    private ObservableList<customersData> customersListData;
    
    public void customersShowData() {
        customersListData = customersDataList();

        customer_col_customer_ID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customer_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        customer_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        customer_col_cashier.setCellValueFactory(new PropertyValueFactory<>("emUsername"));

        cutomer_tableView.setItems(customersListData);
    }
    
    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customer_form.setVisible(false);    
            dashboardDisplayNC();
            dashboardDisplayTI();
            dashboardTotall();
            dashboardNSP();
            dashboardIncomeChart();
            dashboardCustomerChart();
        } else if (event.getSource() == inventort_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(true);
            menu_form.setVisible(false);
            customer_form.setVisible(false);
            inventoryTypeList();
            inventoryStatusList();
            inventoryShowData();
        } else if (event.getSource() == menu_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(true);
            customer_form.setVisible(false);
            menuDisplayCard();
            menuDisplayTotal();
            menuShowOrderData();
        }else if(event.getSource() == customers_btn){
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customer_form.setVisible(true);
            
            customersShowData();
        }
    }
    
    public void logout(){
        
        try{
            
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();
            
            
            if (option.get().equals(ButtonType.OK)){
                logout_btn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);     
                stage.setTitle("Cafe Shop Management Sysytem");    
                stage.setScene(scene);
                stage.show();   
            }
          
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void displayUsername(){
    
        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText(user);    
}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        dashboardDisplayNC();
        dashboardDisplayTI();
        dashboardTotall();
        dashboardNSP();
        dashboardIncomeChart();
        dashboardCustomerChart();    
        inventoryTypeList();
        inventoryStatusList();
        inventoryShowData();
        menuDisplayCard();
        menuGetOrder();
        menuDisplayTotal();
        menuShowOrderData();
        customersShowData();      
    }   
}
