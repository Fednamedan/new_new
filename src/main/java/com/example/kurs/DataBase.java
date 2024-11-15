package com.example.kurs;
//импорт библиотек
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    //переменные для подключения к базе данных
    private final String HOST = "localhost";
    private final String PORT = "13306";
    private final String DB_NAME = "testt";
    private final String LOGIN = "test";
    private final String PASS = "test";
    //установка подключения
    private Connection dbConn = null;
    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME+"?characterEncoding=UTF8";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }


    // вывод информации о товарах
    public ArrayList<ProductData> getProduct() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM towars";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<ProductData> stud = new ArrayList<>();
        while(res.next())
            stud.add(new ProductData(res.getString("nazvanie") ,res.getString("marka") , res.getString("cena"), res.getString("photo")));
        return stud;
    }



    //нахождение id продукта
    public int getIdProduct(String name, String marka, int cena) throws SQLException, ClassNotFoundException{
        String sql = "SELECT idtowars FROM towars where nazvanie = \""+name+"\" and marka = \""+marka+"\" and cena = "+cena+";";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        int id = 0;
        while(res.next())
            id = res.getInt("idtowars");
        return id;

    }



    // изменение данных о продуктах
    void UpdateProduct(String name, String marka, int cena, int id) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE towars set nazvanie =\""+name+"\", marka = \""+marka+"\" ,cena = "+cena+" where idtowars = "+id+";";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.executeUpdate();


    }



    // нахождение городов всех сотрудников
    public ArrayList<String> getCity() throws SQLException, ClassNotFoundException{
        String sql = "SELECT gorod FROM sotrudniki";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        ArrayList<String> citys = new ArrayList<>();
        while(res.next())
            citys.add(res.getString("gorod"));
        return citys;

    }
    //нахождение определенных сотрудников работающих в определенном городе
    public ObservableList<String> getSotrudnik(String city) throws SQLException, ClassNotFoundException{
        String sql = "SELECT fio FROM sotrudniki where gorod = \""+city+"\"";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        ObservableList<String> fio = FXCollections.observableArrayList(); // initialize fio as an empty ObservableList
        while(res.next()) {
            fio.add(res.getString("fio"));
        }
        return fio;
    }

    //нахождение последнего заказа
    public int selZakaz() throws SQLException, ClassNotFoundException{
        String sql = "SELECT idzakazi FROM zakazi ORDER BY idzakazi DESC LIMIT 1";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        int idzak = 0;
        while(res.next()){
            idzak = res.getInt("idzakazi");

        }
        return idzak;
    }


    //нахождение роли юзера по его логину и паролю
    public int getUser(String log,String pass) throws SQLException, ClassNotFoundException {
        String sql = "SELECT userrole , count(*) as n FROM klient where login = ? and password=? group by userrole";

        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, log);
        statement.setString(2, pass);
        ResultSet res = statement.executeQuery();

        int role=0;
        while(res.next()) {
            role = res.getInt("userrole");
        }
        return role;
    }


    // нахождение id пользователя по его логину и паролю
    public int getIDuser(String log,String pass) throws SQLException, ClassNotFoundException {
        String sql = "SELECT idklient  FROM klient where login = ? and password=?";

        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, log);
        statement.setString(2, pass);
        ResultSet res = statement.executeQuery();

        int role=0;
        while(res.next()) {
            role = res.getInt("idklient");
        }
        return role;
    }


    //нахождение id сотрудника
    public int getIdsotrudnik(String fio) throws SQLException, ClassNotFoundException{
        String sql = "SELECT idsotrudniki FROM sotrudniki where fio = \""+fio+"\"";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        int idsotr = 0;
        while(res.next()){
            idsotr = res.getInt("idsotrudniki");
        }
        return idsotr;
    }
    //нахождение последнего заказа сделанного пользователем
    public int getCurrentOrderId(int userId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT max(idzakazi) FROM zakazi WHERE klient_idklient = ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, userId);
        ResultSet res = statement.executeQuery();

        if (res.next()) {
            return res.getInt("max(idzakazi)");
        } else {
            return -1; // or throw an exception
        }
    }

    //вызов процедуры по удалению продукта из заказа
    public void removeOrder(int productId, int orderId) throws SQLException, ClassNotFoundException {
        String sql = "{call removeOrder(?, ?)}";
        CallableStatement statement = getDbConnection().prepareCall(sql);
        statement.setInt(1, productId);
        statement.setInt(2, orderId);
        statement.execute();
    }
    //добовление продукта в заказ
    public void insertOrder(int productId, int orderId, int price) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM towars_has_zakazi WHERE towars_idtowars = ? AND zakazi_idzakazi = ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, productId);
        statement.setInt(2, orderId);
        ResultSet res = statement.executeQuery();

        if (res.next()) {
            // Если запись уже существует, увеличиваем количество
            int newQuantity = res.getInt("kolichestvo") + 1;
            String updateSql = "UPDATE towars_has_zakazi SET kolichestvo = ? WHERE towars_idtowars = ? AND zakazi_idzakazi = ?";
            PreparedStatement updateStatement = getDbConnection().prepareStatement(updateSql);
            updateStatement.setInt(1, newQuantity);
            updateStatement.setInt(2, productId);
            updateStatement.setInt(3, orderId);
            updateStatement.executeUpdate();
        } else {
            // Если записи нет, добавляем новую
            String insertSql = "INSERT INTO towars_has_zakazi (towars_idtowars, zakazi_idzakazi, kolichestvo, cena) VALUES (?, ?, 1, ?)";
            PreparedStatement insertStatement = getDbConnection().prepareStatement(insertSql);
            insertStatement.setInt(1, productId);
            insertStatement.setInt(2, orderId);
            insertStatement.setInt(3, price);
            insertStatement.executeUpdate();
        }
    }


    // создание заказа
    void insertZakazi(String adres_pokupatela, int id_user, int id_sotrudnik)throws SQLException, ClassNotFoundException{
        String sql ="INSERT INTO `mydb`.`zakazi`(adres_pokupatela, klient_idklient, sotrudniki_idsotrudniki) values(?,?,?) ";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, adres_pokupatela);
        prSt.setInt(2, id_user);
        prSt.setInt(3, id_sotrudnik);
        prSt.executeUpdate();
    }
    // полное удаление продукта из базы данных
    void delProduct(String nazvanie, String marka, int cena )throws SQLException, ClassNotFoundException{
        String sql = "Delete from towars where nazvanie = \""+nazvanie+"\" and marka =  \"" +marka+"\"and cena = "+cena+";";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.executeUpdate();
    }
    //добавление пользователя после регистрации

    void insertRegis(String fio ,String gorod , String telefon , String login , String password) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `mydb`.`klient`(`fio`, `gorod`, `telefon`, `login`, `password`,`userrole`) VALUES (?,?,?,?,?,1)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, fio);
        prSt.setString(2, gorod);
        prSt.setString(3, telefon);
        prSt.setString(4, login);
        prSt.setString(5, password);
        prSt.executeUpdate();
    }
    //обавление продукта в базу данных
    void newProduct(String name ,String marka , int cena , int kode ) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `mydb`.`towars`(`nazvanie`, `marka`, `cena`, `kod_tipa`) VALUES (?,?,?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, name);
        prSt.setString(2, marka);
        prSt.setInt(3, cena);
        prSt.setInt(4, kode);
        prSt.executeUpdate();
    }

    //просмотр заказа для пользователя
    public List<OrderDetails> getOrderDetails(int userId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT z.idzakazi as order_id, t.nazvanie as product_name, thz.kolichestvo as quantity, t.cena as price " +
                "FROM towars_has_zakazi thz " +
                "JOIN towars t ON thz.towars_idtowars = t.idtowars " +
                "JOIN zakazi z ON thz.zakazi_idzakazi = z.idzakazi " +
                "JOIN klient k ON z.klient_idklient = k.idklient " +
                "WHERE k.idklient = ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, userId);
        ResultSet res = statement.executeQuery();
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        while (res.next()) {
            int orderId = res.getInt("order_id");
            String productName = res.getString("product_name");
            int quantity = res.getInt("quantity");
            double price = res.getDouble("price");
            orderDetailsList.add(new OrderDetails(orderId, productName, quantity, price));
        }
        return orderDetailsList;
    }



    //проверка перед удалением товара из заказа
    public boolean productExistsInOrder(int productId, int userId) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM towars_has_zakazi JOIN zakazi ON towars_has_zakazi.zakazi_idzakazi = zakazi.idzakazi JOIN klient ON zakazi.klient_idklient = klient.idklient WHERE towars_has_zakazi.towars_idtowars = ? AND klient.idklient = ?";
        PreparedStatement statement = getDbConnection().prepareStatement(query);
        statement.setInt(1, productId);
        statement.setInt(2, userId);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }





}
