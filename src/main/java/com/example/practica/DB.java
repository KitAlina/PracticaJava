package com.example.practica;

import java.sql.*;
import java.util.ArrayList;

public class DB {
    private final String HOST = "127.0.0.1";
    private final String PORT = "13306";
    private final String DB_NAME = "vetcli";
    private final String LOGIN = "javafxTest"; // Если OpenServer, то здесь mysql напишите
    private final String PASS = "7771"; // Если OpenServer, то здесь mysql напишите
    private static String login;
    private static String password;
    private Connection dbConn = null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?characterEncoding=UTF8";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    public int getUsersC(String log, String pas) throws SQLException, ClassNotFoundException {
        String sql = "SELECT role_idrole FROM user where log=? and pass =?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, log);
        statement.setString(2, pas);


        login = log;
        password = pas;

        ResultSet res = statement.executeQuery();
        int col = 0;
        while (res.next()) {
            col = res.getInt(1);
        }
        return col;
    }


    //добавление пользователя владельца
    public void insertUserH(String login, String password) throws SQLException, ClassNotFoundException {
        String sql = "insert into user values (null, ?,?, 1)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        prSt.setString(1, login);
        prSt.setString(2, password);

        prSt.executeUpdate();
    }

    //добавление владельца в одноименную таблицу
    public void insertH(String FIO, int age, String data_birthday, String phone, String address) throws SQLException, ClassNotFoundException {
        String sql = "insert into hosts values (?, ?,?,?,?,?, ?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        prSt.setInt(1, getIDH());
        prSt.setString(2, FIO);
        prSt.setInt(3, age);
        prSt.setString(4, data_birthday);
        prSt.setString(5, phone);
        prSt.setString(6, address);
        prSt.setInt(7, getMaxIdU());

        prSt.executeUpdate();
    }

    //добавление пользователя доктора
    public void insertUserD(String login, String password) throws SQLException, ClassNotFoundException {
        String sql = "insert into user values (null, ?,?, 2)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        prSt.setString(1, login);
        prSt.setString(2, password);

        prSt.executeUpdate();
    }

    //добавление доктора в таблицу доктор
    public void insertD(String FIO, String address, String data_birthday, String phone) throws SQLException, ClassNotFoundException {
        String sql = "insert into doctors values (null, ?,?,?,?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        prSt.setString(1, FIO);
        prSt.setString(2, address);
        prSt.setString(3, data_birthday);
        prSt.setString(4, phone);
        prSt.setInt(5, getMaxIdU());

        prSt.executeUpdate();
    }

    //находим макисмальный id пользователей для добавления в таблицу (доктора, владельцы)
    public int getMaxIdU() throws SQLException, ClassNotFoundException {
        String sql = "SELECT max(iduser) as max FROM user";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        ResultSet res = prSt.executeQuery();
        int num = 0;
        while (res.next()) {
            num = res.getInt("max");
        }
        return num;
    }

    public int getIDH() throws SQLException, ClassNotFoundException {
        String sql = "SELECT max(idhosts)+1 as max FROM hosts";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        ResultSet res = prSt.executeQuery();
        int num = 0;
        while (res.next()) {
            num = res.getInt("max");
        }
        return num;
    }


    public ArrayList<String> getService() throws SQLException, ClassNotFoundException {
        String sql = "SELECT title FROM service";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);

        ResultSet res = statement.executeQuery();
        ArrayList<String> role = new ArrayList<>();
        while (res.next()) {
            role.add(res.getString("title"));
        }
        return role;
    }

    public ArrayList<String> getService_V(String name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT title FROM service where title = ?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        prSt.setString(1, name);

        ResultSet res = prSt.executeQuery();
        ArrayList<String> role = new ArrayList<>();
        while (res.next()) {
            role.add(res.getString("title"));
        }
        return role;
    }
//    public ArrayList<String> getService_ID(String name) throws SQLException, ClassNotFoundException {
//        String sql = "SELECT title FROM service where title = ?";
//        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
//
//        prSt.setString(1, name);
//
//        ResultSet res = prSt.executeQuery();
//        ArrayList<String> role = new ArrayList<>();
//        while (res.next()) {
//            role.add(res.getString("title"));
//        }
//        return role;
//    }
    public ArrayList<String> getDoctors() throws SQLException, ClassNotFoundException {
        String sql = "SELECT FIO FROM doctors";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);

        ResultSet res = statement.executeQuery();
        ArrayList<String> role = new ArrayList<>();
        while (res.next()) {
            role.add(res.getString("FIO"));
        }
        return role;
    }

    public int getIdDoc(String fio) throws SQLException, ClassNotFoundException {
        String sql = "SELECT iddoctors FROM doctors WHERE FIO =?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);

        statement.setString(1, fio);
        ResultSet res = statement.executeQuery();
        int stud = 0;
        while (res.next()) {
            stud = res.getInt("iddoctors");
        }
        return stud;
    }

    public ArrayList<String> getSpec() throws SQLException, ClassNotFoundException {
        String sql = "SELECT title  FROM specialization ";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);

        ResultSet res = statement.executeQuery();
        ArrayList<String> stud = new ArrayList<>();
        while (res.next()) {
            stud.add(res.getString("title"));
        }
        return stud;
    }

    public int getIdSpec(String title) throws SQLException, ClassNotFoundException {
        String sql = "SELECT idspecialization FROM specialization WHERE title =?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);

        statement.setString(1, title);
        ResultSet res = statement.executeQuery();
        int stud = 0;
        while (res.next()) {
            stud = res.getInt("idspecialization");
        }
        return stud;
    }

    public void insertSer(String title, double cost, String desc, int IdSpec) throws SQLException, ClassNotFoundException {
        String sql = "insert into service values (null, ?,?, ?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, title);
        prSt.setDouble(2, cost);
        prSt.setString(3, desc);
        prSt.setInt(4, IdSpec);
        prSt.executeUpdate();
    }

    public void insertSpec(String title) throws SQLException, ClassNotFoundException {
        String sql = "insert into specialization values (null, ?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, title);
        prSt.executeUpdate();
    }

    public int getMaxSer() throws SQLException, ClassNotFoundException {
        String sql = "SELECT max(idservices) as max FROM vetcli.service";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        ResultSet res = prSt.executeQuery();
        int num = 0;
        while (res.next()) {
            num = res.getInt("max");
        }
        return num;
    }

    public void insertDoc_has_Ser(int idser, int iddoc) throws SQLException, ClassNotFoundException {
        String sql = "insert into service_has_doctors values (?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, idser);
        prSt.setInt(2, iddoc);
        prSt.executeUpdate();
//        System.out.println(prSt);
    }

//    public void insertPat(String name, int age, String type_of_animal, String breed) throws SQLException, ClassNotFoundException {
//        String sql = "insert into patients values (null, ?,?,?,?)";
//        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
//        prSt.setString(1, name);
//        prSt.setInt(2, age);
//        prSt.setString(3, type_of_animal);
//        prSt.setString(4, breed);
//        prSt.executeUpdate();
//    }

    public void insertHosts_has_Pat(int idhosts, int idpat) throws SQLException, ClassNotFoundException {
        String sql = "insert into hosts_has_patients values (?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, idhosts);
        prSt.setInt(2, idpat);
        prSt.executeUpdate();

    }

    public ArrayList<String> getHosts() throws SQLException, ClassNotFoundException {
        String sql = "SELECT FIO FROM hosts";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);

        ResultSet res = statement.executeQuery();
        ArrayList<String> hosts = new ArrayList<>();
        while (res.next()) {
            hosts.add(res.getString("FIO"));
        }
        return hosts;
    }

    public int getIdHost(String FIO) throws SQLException, ClassNotFoundException {
        String sql = "SELECT idhosts FROM hosts WHERE FIO =?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);

        statement.setString(1, FIO);
        ResultSet res = statement.executeQuery();
        int IDHos = 0;
        while (res.next()) {
            IDHos = res.getInt("idhosts");
        }
        return IDHos;
    }

    public int getMaxPat() throws SQLException, ClassNotFoundException {
        String sql = "SELECT max(idpatients) as max FROM vetcli.patients";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        ResultSet res = prSt.executeQuery();
        int num = 0;
        while (res.next()) {
            num = res.getInt("max");
        }
        return num;
    }
    public int getMaxPat1() throws SQLException, ClassNotFoundException {
        String sql = "SELECT max(idpatients)+1 as max FROM vetcli.patients";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        ResultSet res = prSt.executeQuery();
        int num = 0;
        while (res.next()) {
            num = res.getInt("max");
        }
        return num;
    }
    public ArrayList<String> getDes() throws SQLException, ClassNotFoundException {
        String sql = "SELECT description FROM service";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);

        ResultSet res = statement.executeQuery();
        ArrayList<String> desc = new ArrayList<>();
        while (res.next()) {
            desc.add(res.getString("description"));
        }
        return desc;
    }

    public ArrayList<Double> getCost() throws SQLException, ClassNotFoundException {
        String sql = "SELECT cost FROM service";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);

        ResultSet res = statement.executeQuery();
        ArrayList<Double> role = new ArrayList<>();
        while (res.next()) {
            role.add(res.getDouble("cost"));
        }
        return role;
    }

    public ArrayList<Double> getCost_V(String name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT cost FROM service where title = ?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);

        statement.setString(1, name);
        ResultSet res = statement.executeQuery();
        ArrayList<Double> role = new ArrayList<>();
        while (res.next()) {
            role.add(res.getDouble("cost"));
        }
        return role;
    }


    //delete
    public void deleteSer(String id) throws SQLException, ClassNotFoundException {
        String sql = "delete from service where idservices =?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, id);

        prSt.executeUpdate();
    }

    public void deleteHost(String id) throws SQLException, ClassNotFoundException {
        String sql = "delete from hosts where idhosts =?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, id);

        prSt.executeUpdate();
    }
    public void geleteHos_H_Pat(int idhosts)throws SQLException, ClassNotFoundException {
        String sql = "delete from hosts_has_patients where hosts_idhosts =?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, idhosts);

        prSt.executeUpdate();
    }
    public void deleteTime(int idd, int idc) throws SQLException, ClassNotFoundException {
        String sql = "delete from doctors_has_calendar where doctors_iddoctors =? and calendar_idcalendar=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, idd);
        prSt.setInt(2, idc);
        prSt.executeUpdate();
    }
    public void deleteDoc(String id) throws SQLException, ClassNotFoundException {
        String sql = "delete from doctors where iddoctors =?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, id);

        prSt.executeUpdate();
    }
    public void deleteSer_h_D(String id) throws SQLException, ClassNotFoundException {
        String sql = "delete from service_has_doctors where doctors_iddoctors =?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, id);

        prSt.executeUpdate();
    }
    public void deleteSer_h_DS(String id) throws SQLException, ClassNotFoundException {
        String sql = "delete from service_has_doctors where service_idservices =?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, id);

        prSt.executeUpdate();
    }
    public void deleteUserD(String id) throws SQLException, ClassNotFoundException {
        String sql = "delete from user where iduser=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, id);

        prSt.executeUpdate();
    }
    public void deletePatients(String id) throws SQLException, ClassNotFoundException {
        String sql = "delete from doctors where iddoctors =?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, id);

        prSt.executeUpdate();
    }


    public ArrayList<String> getDay() throws SQLException, ClassNotFoundException {
        String sql = "SELECT DISTINCT day FROM calendar";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);

        ResultSet res = statement.executeQuery();
        ArrayList<String> role = new ArrayList<>();
        while (res.next()) {
            role.add(res.getString("day"));
        }
        return role;
    }

    public ArrayList<Time> getOclock() throws SQLException, ClassNotFoundException {
        String sql = "SELECT DISTINCT time FROM calendar";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);

        ResultSet res = statement.executeQuery();
        ArrayList<Time> time = new ArrayList<>();
        while (res.next()) {
            time.add(res.getTime("time"));
        }
        return time;
    }


    //    для составления расписания нам необходимо получить id доктора (getIdDoc),
//    затем мы находим id calendar по day и time из ComboBox
    public int getIdCal(String day, Time time) throws SQLException, ClassNotFoundException {
        String sql = "SELECT idcalendar FROM calendar WHERE day =? and time =?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);

        statement.setString(1, day);
        statement.setTime(2, time);
        ResultSet res = statement.executeQuery();
        int stud = 0;
        while (res.next()) {
            stud = res.getInt("idcalendar");
        }
        return stud;
    }

    public int getIdTime(int doc, int idc) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id_time FROM doctors_has_calendar WHERE doctors_iddoctors =? and calendar_idcalendar =?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);

        statement.setInt(1, doc);
        statement.setInt(2, idc);
        ResultSet res = statement.executeQuery();
        int stud = 0;
        while (res.next()) {
            stud = res.getInt("id_time");
        }
        return stud;
    }

    public void insertCalendar(String date, Time time) throws SQLException, ClassNotFoundException {
        String sql = "insert into calendar values ( null,?, ?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, date);
        prSt.setTime(2, time);

        prSt.executeUpdate();
//        System.out.println(prSt);
    }

    public void insertD_has_C(int iddoc, int getIdCal) throws SQLException, ClassNotFoundException {
        String sql = "insert into doctors_has_calendar values ( ?,?, ?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, iddoc);
        prSt.setInt(2, getIdCal);
        prSt.setInt(3, getMaxTime());
        prSt.executeUpdate();
//        System.out.println(prSt);
    }
    public void updateD_has_C(int iddoc, int getIdCal) throws SQLException, ClassNotFoundException {
        String sql = "update doctors set  where doctors_id_doctors=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, iddoc);
        prSt.setInt(2, getIdCal);
        prSt.setInt(3, getMaxTime());
        prSt.executeUpdate();
//        System.out.println(prSt);
    }
//вывод ш


    public int getMaxTime() throws SQLException, ClassNotFoundException {
        String sql = "SELECT max(id_time)+1 as max FROM vetcli.doctors_has_calendar";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        ResultSet res = prSt.executeQuery();
        int num = 0;
        while (res.next()) {
            num = res.getInt("max");
        }
        return num;
    }


    //получить айди пользователя по лог и пас
    public int getID_Us_for_Host() throws SQLException, ClassNotFoundException {
        String sql = "SELECT iduser FROM user WHERE log=? and pass=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, login);
        statement.setString(2, password);
        ResultSet res = statement.executeQuery();
        int col = 0;
        while (res.next()) {
            col = res.getInt(1);
        }
        return col;
    }

    // по айди пользователя найти айди хоста
    public int getIDHostZ(int idUser) throws SQLException, ClassNotFoundException {
        String sql = "SELECT idhosts FROM hosts WHERE user_iduser=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, idUser);
        ResultSet res = statement.executeQuery();

        int num = 0;
        while (res.next()) {
            num = res.getInt("idhosts");
        }
        return num;
    }


    // по айди хоста найти айди питомцев(пациентов) - поиск по смежной таблице
    public ArrayList<Integer> getidPat(int idhost) throws SQLException, ClassNotFoundException {
        String sql = "SELECT patients_idpatients FROM hosts_has_patients WHERE hosts_idhosts=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, idhost);
        ResultSet res = statement.executeQuery();
        ArrayList<Integer> integer = new ArrayList<>();
        while (res.next()) {
            integer.add(res.getInt("patients_idpatients"));
        }
        return integer;
    }

// по айди питомцев найти их имена - таблица пациентов

    public ArrayList<String> getName_Pat(int idpat) throws SQLException, ClassNotFoundException {
        String sql = "SELECT name FROM patients WHERE idpatients=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, idpat);
        ResultSet res = statement.executeQuery();
        ArrayList<String> integer = new ArrayList<>();
        while (res.next()) {
            integer.add(res.getString("name"));
        }
        return integer;
    }

//    public String getNamePat(int idpat) throws SQLException, ClassNotFoundException {
//        String sql = "SELECT name FROM patients WHERE idpatients=?";
//        PreparedStatement statement = getDbConnection().prepareStatement(sql);
//        statement.setInt(1, idpat);
//        ResultSet res = statement.executeQuery();
//        String name = "";
//        while (res.next()) {
//            res.getString("name");
//        }
//        return name;
//    }
    //получаем id Service выбранного в TableView в Zapis
    public int IdSerZ(String title) throws SQLException, ClassNotFoundException {
        String sql = "SELECT idservices FROM service WHERE title=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, title);
        ResultSet res = statement.executeQuery();

        int num = 0;
        while (res.next()) {
            num = res.getInt("idservices");
        }
        return num;
    }

    //получаем id Doctor из смежной таблички D-S
    public ArrayList<Integer> idDocZ(int idSerZ) throws SQLException, ClassNotFoundException {
        String sql = "SELECT doctors_iddoctors FROM service_has_doctors WHERE service_idservices=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, idSerZ);
        ResultSet res = statement.executeQuery();
        ArrayList<Integer> integer = new ArrayList<>();
        while (res.next()) {
            integer.add(res.getInt("doctors_iddoctors"));
        }
        return integer;
    }

    //получаем по id доктора получаем найди календаря
    public ArrayList<Integer> idcalendar(int idDoc) throws SQLException, ClassNotFoundException {
        String sql = "SELECT calendar_idcalendar FROM doctors_has_calendar WHERE doctors_iddoctors=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, idDoc);
        ResultSet res = statement.executeQuery();
        ArrayList<Integer> integer = new ArrayList<>();
        while (res.next()) {
            integer.add(res.getInt("calendar_idcalendar"));
        }
        return integer;
    }

    //получаем по id календаря получаем день
    public String idday(int idCal) throws SQLException, ClassNotFoundException {
        String sql = "SELECT DISTINCT day FROM calendar WHERE idcalendar=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, idCal);
        ResultSet res = statement.executeQuery();

        String day = null;
        while (res.next()) {
            day = res.getString("day");
        }
        return day;
    }

    //получаем по id календаря получаем день
    public Date idday1(int idCal) throws SQLException, ClassNotFoundException {
        String sql = "SELECT DISTINCT day1 FROM calendar WHERE idcalendar=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, idCal);
        ResultSet res = statement.executeQuery();

        Date day = null;
        while (res.next()) {
            day = res.getDate("day1");
        }
        return day;
    }


    //получаем по id календаря получаем time
    public Time idtime(int idCal) throws SQLException, ClassNotFoundException {
        String sql = "SELECT time FROM calendar WHERE idcalendar=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, idCal);
        ResultSet res = statement.executeQuery();

        Time num = null;
        while (res.next()) {
            num = res.getTime("time");
        }
        return num;
    }

    public void insertS_h_P(int idpat, int idser, int idCal) throws SQLException, ClassNotFoundException {
        String sql = "insert into patients_has_service values (null, ?,?, ?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, idpat);
        prSt.setInt(2, idser);
        prSt.setInt(3, idCal);
        prSt.executeUpdate();
//        System.out.println(prSt);
    }




    public int idPat (String name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT idpatients FROM patients WHERE name=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, name);
        ResultSet res = statement.executeQuery();

        int id = 0;
        while (res.next()) {
            id = res.getInt("idpatients");
        }
        return id;
    }

    public ArrayList<Integer> getidSer(int idPat) throws SQLException, ClassNotFoundException {
        String sql = "SELECT service_idservices FROM patients_has_service WHERE patients_idpatients=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, idPat);
        ResultSet res = statement.executeQuery();
        ArrayList<Integer> integer = new ArrayList<>();
        while (res.next()) {
            integer.add(res.getInt("service_idservices"));
        }
        return integer;
    }

    public ArrayList<String> getServiceP(int idSER) throws SQLException, ClassNotFoundException {
        String sql = "SELECT title FROM service WHERE idservices=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, idSER);
        ResultSet res = statement.executeQuery();
        ArrayList<String> role = new ArrayList<>();
        while (res.next()) {
            role.add(res.getString("title"));
        }
        return role;

    }
    public ArrayList<Double> getCostH(int idSer) throws SQLException, ClassNotFoundException {
        String sql = "SELECT cost FROM service WHERE idservices=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, idSer);
        ResultSet res = statement.executeQuery();
        ArrayList<Double> role = new ArrayList<>();
        while (res.next()) {
            role.add(res.getDouble("cost"));
        }
        return role;

    }

    public void insertSer_h_Doc(int idser, int doctors) throws SQLException, ClassNotFoundException  {
        String sql = "insert into service_has_doctors values (?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, idser);
        prSt.setInt(2, doctors);
        prSt.executeUpdate();
//        System.out.println(prSt);
    }

    public int getIDService(String title) throws SQLException, ClassNotFoundException {
        String sql = "SELECT idservices FROM service where title = '" + title+ "'";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);

        ResultSet res = statement.executeQuery();
        int t = 0;
        while (res.next()) {
            t = res.getInt("idservices");
        }
        return t;
    }

    //находим макисмальный id доктора для добавления в таблицу (доктора)
    public int getMaxIdD() throws SQLException, ClassNotFoundException {
        String sql = "SELECT max(iddoctors) as max FROM doctors";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);

        ResultSet res = prSt.executeQuery();
        int num = 0;
        while (res.next()) {
            num = res.getInt("max");
        }
        return num;
    }
    public ArrayList<String> NamePat() throws SQLException,ClassNotFoundException{
        String sql = "SELECT name FROM patients ";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);

        ResultSet res = statement.executeQuery();
        ArrayList<String> name = new ArrayList<>();
        while (res.next()) {
            name.add(res.getString("name"));
        }
        return name;
    }

    public String getOneFioDoctor(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT FIO FROM doctors where iddoctors=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();

        String tasks = "";
        while (res.next()) {
            tasks = res.getString("FIO");
        }
        return tasks;
    }
    public Date getOneDataDoctor(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT data_birthday FROM doctors where iddoctors=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();

        Date tasks = null;
        while (res.next()) {
            tasks = res.getDate("data_birthday");
        }
        return tasks;
    }
    public String getOneAddressDoctor(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT address FROM doctors where iddoctors=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();

        String tasks = null;
        while (res.next()) {
            tasks = res.getString("address");
        }
        return tasks;
    }

    public String getOnePhoneDoctor(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT phone FROM doctors where iddoctors=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();

        String tasks = null;
        while (res.next()) {
            tasks = res.getString("phone");
        }
        return tasks;
    }

    public void updateDoctors(String fio, String address, Date date, String phone, int idD) throws SQLException, ClassNotFoundException {
        String sql = "update doctors set FIO =?, address=?, data_birthday =?, phone=? where iddoctors=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, fio);
        prSt.setString(2, address);
        prSt.setDate(3, date);
        prSt.setString(4, phone);
        prSt.setInt(5, idD);

        prSt.executeUpdate();
    }

    public void updateUser(String log, String pass, int idUser) throws SQLException, ClassNotFoundException {
        String sql = "update user set log =?, pass=? where iduser=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, log);
        prSt.setString(2, pass);
        prSt.setInt(3, idUser);

        prSt.executeUpdate();
    }
    public void updateSer_h_Doc(int idser, int doctors) throws SQLException, ClassNotFoundException  {
        String sql = "update service_has_doctors  set service_idservices =? where doctors_iddoctors=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, idser);
        prSt.setInt(2, doctors);
        prSt.executeUpdate();
//        System.out.println(prSt);
    }

    public int getUsersIDDoc(String fio) throws SQLException, ClassNotFoundException {
        String sql = "SELECT user_iduser FROM doctors where FIO=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, fio);

        ResultSet res = statement.executeQuery();
        int col = 0;
        while (res.next()) {
            col = res.getInt("user_iduser");
        }
        return col;
    }

    public String getOnelogUser(int gUsIdD) throws SQLException, ClassNotFoundException {
        String sql = "SELECT log FROM user where iduser=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, gUsIdD);
        ResultSet res = statement.executeQuery();

        String tasks = null;
        while (res.next()) {
            tasks = res.getString("log");
        }
        return tasks;
    }
    public String getOnePassUser(int gUsIdD) throws SQLException, ClassNotFoundException {
        String sql = "SELECT pass FROM user where iduser=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, gUsIdD);
        ResultSet res = statement.executeQuery();

        String tasks = null;
        while (res.next()) {
            tasks = res.getString("pass");
        }
        return tasks;
    }

//public ArrayList<Integer> idService (int idDoc) throws SQLException, ClassNotFoundException {
//    String sql = "SELECT service_idservices FROM service_has_doctors where doctors_iddoctors=?";
//    PreparedStatement statement = getDbConnection().prepareStatement(sql);
//    statement.setInt(1, idDoc);
//    ResultSet res = statement.executeQuery();
//
//    ArrayList<Integer> role = new ArrayList<>();
//    while (res.next()) {
//        role.add(res.getInt("service_idservices"));
//    }
//    return role;
//}
    public String getOneFioHosts(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT FIO FROM hosts where idhosts=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();

        String tasks = "";
        while (res.next()) {
            tasks = res.getString("FIO");
        }
        return tasks;
    }
    public int getOneAgeHosts(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT age FROM hosts where idhosts=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();

        int tasks = 0;
        while (res.next()) {
            tasks = res.getInt("age");
        }
        return tasks;
    }
    public Date getOneDataHosts(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT data_birthday FROM hosts where idhosts=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();

        Date tasks = null;
        while (res.next()) {
            tasks = res.getDate("data_birthday");
        }
        return tasks;
    }
    public String getOneAddressHosts(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT address FROM hosts where idhosts=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();

        String tasks = null;
        while (res.next()) {
            tasks = res.getString("address");
        }
        return tasks;
    }

    public String getOnePhoneHosts(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT phone FROM hosts where idhosts=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();

        String tasks = null;
        while (res.next()) {
            tasks = res.getString("phone");
        }
        return tasks;
    }

    public int getUsersIDHos(String fio) throws SQLException, ClassNotFoundException {
        String sql = "SELECT user_iduser FROM hosts where FIO=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, fio);

        ResultSet res = statement.executeQuery();
        int col = 0;
        while (res.next()) {
            col = res.getInt("user_iduser");
        }
        return col;
    }
    public ArrayList<String> getPatients() throws SQLException, ClassNotFoundException {
        String sql = "SELECT name FROM patients";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);

        ResultSet res = statement.executeQuery();
        ArrayList<String> role = new ArrayList<>();
        while (res.next()) {
            role.add(res.getString("name"));
        }
        return role;
    }
    public void updateHosts(String fio, int age, Date data, String phone, String address, int idH) throws SQLException, ClassNotFoundException {
        String sql = "update hosts set FIO =?, age =?, data_birthday=?,  phone=?, address=? where idhosts=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, fio);
        prSt.setInt(2, age);
        prSt.setDate(3,data);
        prSt.setString(4, phone);
        prSt.setString(5, address);
        prSt.setInt(6, idH);

        prSt.executeUpdate();
    }
    public void updateHos_H_Pati(int hosts, int pati) throws SQLException, ClassNotFoundException  {
        String sql = "update hosts_has_patients set hosts_idhosts =? where patients_idpatients=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, hosts);
        prSt.setInt(2, pati);
        prSt.executeUpdate();
//        System.out.println(prSt);
    }
    public String getOneTitleSer(int idSer) throws SQLException, ClassNotFoundException {
        String sql = "SELECT title FROM service where idservices=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, idSer);
        ResultSet res = statement.executeQuery();

        String tasks = "";
        while (res.next()) {
            tasks = res.getString("title");
        }
        return tasks;
    }
    public String getOneDesc(int idSer) throws SQLException, ClassNotFoundException {
        String sql = "SELECT description FROM service where idservices=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, idSer);
        ResultSet res = statement.executeQuery();

        String tasks = "";
        while (res.next()) {
            tasks = res.getString("description");
        }
        return tasks;
    }
    public Double getOneCost(int idSer) throws SQLException, ClassNotFoundException {
        String sql = "SELECT cost FROM service where idservices=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, idSer);
        ResultSet res = statement.executeQuery();

        Double tasks = null;
        while (res.next()) {
            tasks = res.getDouble("cost");
        }
        return tasks;
    }
    public void updateSer(String title, double cost, String desc, int spec, int Ser) throws SQLException, ClassNotFoundException {
        String sql = "update service set title =?, cost =?, description=?,  specialization_idspecialization=? where idservices=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, title);
        prSt.setDouble(2, cost);
        prSt.setString(3, desc);
        prSt.setInt(4, spec);
        prSt.setInt(5, Ser);

        prSt.executeUpdate();
    }

    public String getOneName(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT name FROM patients where idpatients=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();

        String tasks = "";
        while (res.next()) {
            tasks = res.getString("name");
        }
        return tasks;
    }

    public int getOneAge(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT age FROM patients where idpatients=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();

        int tasks = 0;
        while (res.next()) {
            tasks = res.getInt("age");
        }
        return tasks;
    }
    public String getOneType_of_animal(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT type_of_animal FROM patients where idpatients=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();

        String tasks = "";
        while (res.next()) {
            tasks = res.getString("type_of_animal");
        }
        return tasks;
    }
    public String getOneBreed(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT breed FROM patients where idpatients=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();

        String tasks = "";
        while (res.next()) {
            tasks = res.getString("breed");
        }
        return tasks;
    }
    public void updatePat(String name, int age, String type_of_animal, String breed, int idpat) throws SQLException, ClassNotFoundException {
        String sql = "update patients set name =?, age =?, type_of_animal=?,  breed=? where idpatients=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, name);
        prSt.setInt(2, age);
        prSt.setString(3, type_of_animal);
        prSt.setString(4, breed);
        prSt.setInt(5, idpat);
        prSt.executeUpdate();
    }

    public void updatePat_h_Hos(int hos, int pat) throws SQLException, ClassNotFoundException  {
        String sql = "update hosts_has_patients set hosts_idhosts =? where patients_idpatients=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, hos);
        prSt.setInt(2, pat);
        prSt.executeUpdate();
//        System.out.println(prSt);
    }
    public int getidPatInt(int idpat) throws SQLException, ClassNotFoundException {
        String sql = "SELECT hosts_idhosts FROM hosts_has_patients WHERE patients_idpatients=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setInt(1, idpat);
        ResultSet res = statement.executeQuery();
         int id =0;
        while (res.next()) {
            res.getInt("hosts_idhosts");
        }
        return id;
    }
    public void deleteHos_H_Pat(int idpat)throws SQLException, ClassNotFoundException {
        String sql = "delete from hosts_has_patients where patients_idpatients =?";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setInt(1, idpat);

        prSt.executeUpdate();
    }
    public String insertPat_Procedura(int gMP, String name, int age, String type_of_animal, String breed)throws SQLException, ClassNotFoundException{
        String v = "";
        CallableStatement proc = getDbConnection().prepareCall("CALL add_patient(?, ?,?,?,?)");
        proc.setInt(1, gMP);
        proc.setString(2, name);
        proc.setInt(3, age);
        proc.setString(4,type_of_animal);
        proc.setString(5, breed);
        ResultSet res = proc.executeQuery();
        while (res.next()){
            v = res.getString("Успешно обновлено!");
        }

        return v;
    }

}






