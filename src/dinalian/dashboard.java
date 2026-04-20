/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package dinalian;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Huntz Rendever
 */
public class dashboard extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(dashboard.class.getName());
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dinalian_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    private Color originalCreateColor = new Color(153, 102, 255);
    private Color hoverColor = new Color(180, 130, 255);
    private Color clickColor = new Color(120, 70, 220);
    private List<String[]> flowerData = new ArrayList<>();
    private Timer animationTimer;
    private float tableOpacity = 0f;
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
    public dashboard() {
        initComponents();
        setupButtonEffects();
        setupTableAnimation();
        loadFlowers();
    }
    
    private void setupButtonEffects() {
        addButtonHoverEffect(create);
        addButtonHoverEffect(delete);
        addButtonHoverEffect(read);
        addButtonHoverEffect(update);
        addButtonHoverEffect(logout);
    }
    
    private void addButtonHoverEffect(javax.swing.JButton button) {
        button.setBackground(originalCreateColor);
        button.setOpaque(true);
        button.setContentAreaFilled(false);
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
                button.setOpaque(true);
                button.setContentAreaFilled(true);
                button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalCreateColor);
                button.setOpaque(true);
                button.setContentAreaFilled(true);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(clickColor);
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(hoverColor);
            }
        });
    }
    
    private void setupTableAnimation() {
        tableOpacity = 0f;
        animationTimer = new Timer(30, e -> {
            tableOpacity += 0.1f;
            if (tableOpacity >= 1f) {
                tableOpacity = 1f;
                ((Timer) e.getSource()).stop();
            }
            jScrollPane1.repaint();
        });
    }
    
    private void loadFlowers() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        flowerData.clear();
        
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM flowers ORDER BY id DESC";
            try (PreparedStatement pstmt = conn.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String[] row = {
                        rs.getString("name"),
                        rs.getString("type"),
                        String.format("%.2f", rs.getDouble("price"))
                    };
                    flowerData.add(row);
                }
            }
            
            model.setRowCount(0);
            for (String[] row : flowerData) {
                model.addRow(row);
            }
            
            if (!flowerData.isEmpty()) {
                startTableAnimation();
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading flowers: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void startTableAnimation() {
        tableOpacity = 0f;
        jTable1.setForeground(new Color(0, 0, 0, 0));
        animationTimer.start();
        
        new Thread(() -> {
            try {
                Thread.sleep(200);
                javax.swing.SwingUtilities.invokeLater(() -> {
                    jTable1.setForeground(Color.BLACK);
                    jScrollPane1.repaint();
                });
            } catch (InterruptedException ex) {
            }
        }).start();
    }
    
    private void searchFlowers(String searchTerm) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        if (searchTerm.isEmpty()) {
            for (String[] row : flowerData) {
                model.addRow(row);
            }
            return;
        }
        
        String lowerSearch = searchTerm.toLowerCase();
        for (String[] row : flowerData) {
            if (row[0].toLowerCase().contains(lowerSearch) || 
                row[1].toLowerCase().contains(lowerSearch) ||
                row[2].toLowerCase().contains(lowerSearch)) {
                model.addRow(row);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        create = new javax.swing.JButton();
        logout = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        delete = new javax.swing.JButton();
        read = new javax.swing.JButton();
        update = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        searchLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(153, 102, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 255, 0), new java.awt.Color(153, 0, 255)));

        create.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        create.setText("ADD");
        create.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        create.addActionListener(this::createActionPerformed);

        logout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        logout.setText("LOGOUT");
        logout.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        logout.addActionListener(this::logoutActionPerformed);

        jLabel2.setFont(new java.awt.Font("Tempus Sans ITC", 1, 18)); // NOI18N
        jLabel2.setText("Actions");

        delete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        delete.setText("DELETE");
        delete.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        delete.addActionListener(this::deleteActionPerformed);

        read.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        read.setText("READ");
        read.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        read.addActionListener(this::readActionPerformed);

        update.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        update.setText("UPDATE");
        update.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        update.addActionListener(this::updateActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(read, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(create, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(delete, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addComponent(update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(logout))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(create, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(read, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(logout)
                .addGap(20, 20, 20))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 240, 230));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tempus Sans ITC", 3, 36)); // NOI18N
        jLabel3.setText("FlOWER SHOP");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 290, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinalian/background.jpg"))); // NOI18N
        jButton2.setText("jButton2");
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 8, 617, 106));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 630, 120));

        searchLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));
        searchLabel.setText("Search:");
        searchLabel.setForeground(new java.awt.Color(255, 255, 0));
        jPanel1.add(searchLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, 60, 25));

        searchField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0), 2));
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchFieldKeyReleased(evt);
            }
        });
        jPanel1.add(searchField, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, 200, 25));

        jTable1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Flowername", "type of flower", "price"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, 510, 240));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dinalian/ggg.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0), 5));
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 420));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            animateErrorMessage("Please select a flower to delete");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this flower?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        String name = (String) model.getValueAt(selectedRow, 0);
        
        try (Connection conn = getConnection()) {
            String query = "DELETE FROM flowers WHERE name = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, name);
                pstmt.executeUpdate();
                animateSuccessMessageWithEffect("Flower deleted successfully!");
                loadFlowers();
            }
        } catch (SQLException ex) {
            animateErrorMessage("Database error: " + ex.getMessage());
        }
    }

    private void animateErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void animateSuccessMessageWithEffect(String message) {
        javax.swing.JLabel label = new javax.swing.JLabel(message);
        label.setFont(new java.awt.Font("Segoe UI", 1, 14));
        label.setForeground(new Color(0, 150, 0));
        JOptionPane.showMessageDialog(this, label, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void createActionPerformed(java.awt.event.ActionEvent evt) {
        String name = JOptionPane.showInputDialog(this, "Enter Flower Name:");
        if (name == null || name.trim().isEmpty()) return;
        
        String type = JOptionPane.showInputDialog(this, "Enter Flower Type:");
        if (type == null || type.trim().isEmpty()) return;
        
        String priceStr = JOptionPane.showInputDialog(this, "Enter Price:");
        if (priceStr == null || priceStr.trim().isEmpty()) return;
        
        try {
            double price = Double.parseDouble(priceStr);
            try (Connection conn = getConnection()) {
                String query = "INSERT INTO flowers (name, type, price) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, type);
                    pstmt.setDouble(3, price);
                    pstmt.executeUpdate();
                    animateSuccessMessageWithEffect("Flower added successfully!");
                    loadFlowers();
                }
            }
        } catch (NumberFormatException ex) {
            animateErrorMessage("Invalid price format");
        } catch (SQLException ex) {
            animateErrorMessage("Database error: " + ex.getMessage());
        }
    }

    private void readActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = jTable1.getSelectedRow();
        
        if (selectedRow == -1) {
            animateErrorMessage("Please select a flower to view details");
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        String name = (String) model.getValueAt(selectedRow, 0);
        String type = (String) model.getValueAt(selectedRow, 1);
        String price = String.valueOf(model.getValueAt(selectedRow, 2));
        
        String details = "<html><body>"
                + "<h style='color: #6A0DAD; font-size: 16px;'>Flower Details</h><br><br>"
                + "<b>Flower Name:</b> " + name + "<br><br>"
                + "<b>Flower Type:</b> " + type + "<br><br>"
                + "<b>Price:</b> $" + price
                + "</body></html>";
        
        JLabel label = new JLabel(details);
        label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        JOptionPane.showMessageDialog(this, label, "View Flower", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void searchFieldKeyReleased(java.awt.event.KeyEvent evt) {
        String searchTerm = searchField.getText().trim();
        searchFlowers(searchTerm);
    }
    
    private void animateSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            animateErrorMessage("Please select a flower to update");
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        String currentName = (String) model.getValueAt(selectedRow, 0);
        String currentType = (String) model.getValueAt(selectedRow, 1);
        String currentPrice = String.valueOf(model.getValueAt(selectedRow, 2));
        
        String name = JOptionPane.showInputDialog(this, "Enter Flower Name:", currentName);
        if (name == null || name.trim().isEmpty()) return;
        
        String type = JOptionPane.showInputDialog(this, "Enter Flower Type:", currentType);
        if (type == null || type.trim().isEmpty()) return;
        
        String priceStr = JOptionPane.showInputDialog(this, "Enter Price:", currentPrice);
        if (priceStr == null || priceStr.trim().isEmpty()) return;
        
        try {
            double price = Double.parseDouble(priceStr);
            try (Connection conn = getConnection()) {
                String query = "UPDATE flowers SET name = ?, type = ?, price = ? WHERE name = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, type);
                    pstmt.setDouble(3, price);
                    pstmt.setString(4, currentName);
                    pstmt.executeUpdate();
                    animateSuccessMessageWithEffect("Flower updated successfully!");
                    loadFlowers();
                }
            }
        } catch (NumberFormatException ex) {
            animateErrorMessage("Invalid price format");
        } catch (SQLException ex) {
            animateErrorMessage("Database error: " + ex.getMessage());
        }
    }

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            login loginFrame = new login();
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            dashboard frame = new dashboard();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton create;
    private javax.swing.JButton delete;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton logout;
    private javax.swing.JButton read;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}
