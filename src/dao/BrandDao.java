package dao;

import core.DB;
import entity.Brand;
import entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BrandDao {
    private final Connection connection;

    public BrandDao() {
        this.connection = DB.getInstance();
    }

    public ArrayList<Brand> findAll() {
        ArrayList<Brand> brandList = new ArrayList<>();
        String sql = "SELECT * FROM public.brand";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                brandList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandList;
    }

    public Brand match(ResultSet rs) {
        Brand obj = new Brand();
        try {
            obj.setId(rs.getInt("brand_id"));
            obj.setName(rs.getString("brand_name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
