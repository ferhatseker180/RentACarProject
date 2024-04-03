package view;

import business.BrandManager;
import business.ModelManager;
import core.Helper;
import entity.Model;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminView extends Layout {
    private JPanel container;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JTabbedPane tab_menu;
    private JButton btn_logout;
    private JPanel pnl_brand;
    private JScrollPane scrl_brand;
    private JTable tbl_brand;
    private JPanel pnl_model;
    private JScrollPane scrl_model;
    private JTable tbl_model;
    private User user;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private DefaultTableModel tmdl_model = new DefaultTableModel();
    private BrandManager brandManager;
    private ModelManager modelManager;
    private JPopupMenu brand_menu;
    private JPopupMenu model_menu;

    public AdminView(User user) {
        this.brandManager = new BrandManager();
        this.modelManager = new ModelManager();
        this.add(container);
        this.guiInitilaze(400, 400);
        this.user = user;

        if (this.user == null) {
            dispose(); // dispose() : Bir işlemi bellekten atar, close işlemi durdursa da bellekte tutar ancak dispose bellekten de atar.
        }
        this.lbl_welcome.setText("Welcome " + this.user.getUsername());
        loadBrandTable();
        loadBrandComponent();

        loadModelTable();
        loadModelComponent();

        this.tbl_brand.setComponentPopupMenu(brand_menu);
        this.tbl_model.setComponentPopupMenu(model_menu);
    }

    public void loadBrandComponent() {

        tableRowSelect(this.tbl_brand);
        this.brand_menu = new JPopupMenu();
        this.brand_menu.add("New").addActionListener(e -> {
            BrandView brandView = new BrandView(null); // Ekleme yaptığımdan null gönderiyorum.
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable();
                }
            });
        });
        this.brand_menu.add("Update").addActionListener(e -> {
            int selectBrandId = this.getTableSelectedRow(this.tbl_brand, 0);
            BrandView brandView = new BrandView(this.brandManager.getById(selectBrandId));
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable();
                }
            });
        });
        this.brand_menu.add("Delete").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectBrandId = this.getTableSelectedRow(this.tbl_brand, 0);
                if (this.brandManager.delete(selectBrandId)) {
                    Helper.showMessage("done");
                    loadBrandTable();
                    loadModelTable();
                } else {
                    Helper.showMessage("error");
                }
            }

        });
    }

    public void loadModelComponent() {
        tableRowSelect(this.tbl_model);
        this.model_menu = new JPopupMenu();

        this.model_menu.add("New").addActionListener(e -> {
            ModelView modelView = new ModelView(new Model());
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable();
                }
            });
        });
        this.model_menu.add("Update").addActionListener(e -> {
            int selectModelId = this.getTableSelectedRow(this.tbl_model, 0);
            ModelView modelView = new ModelView(this.modelManager.getById(selectModelId));
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable();
                }
            });

        });
        this.model_menu.add("Delete").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectModelId = this.getTableSelectedRow(this.tbl_model, 0);
                if (this.modelManager.delete(selectModelId)) {
                    Helper.showMessage("done");
                    loadModelTable();
                } else {
                    Helper.showMessage("error");
                }
            }
        });

    }

    public void loadBrandTable() {
        Object[] col_brand = {"Brand ID", "Brand Name"};
        ArrayList<Object[]> brandList = this.brandManager.getForTable(col_brand.length);
        this.createTable(this.tmdl_brand, this.tbl_brand, col_brand, brandList);
    }

    public void loadModelTable() {
        Object[] col_model = {"Model ID", "Brand", "Model Name", "Type", "Year", "Fuel", "Gear"};
        ArrayList<Object[]> modelList = this.modelManager.getForTable(col_model.length, this.modelManager.findAll());
        this.createTable(this.tmdl_model, this.tbl_model, col_model, modelList);
    }
}
