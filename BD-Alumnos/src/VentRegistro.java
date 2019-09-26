import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VentRegistro extends JFrame implements ActionListener {

    private JButton botR, botM, botE, botB;
    private JTextField txtN, txtG, txtB;
    private JLabel lN, lG, lB, lS;

    public VentRegistro() {
        setLayout(null);
        setTitle("Registro de Alumnos");

        //label Nombre
        lN = new JLabel("Nombre");
        lN.setBounds(10, 10, 100, 30);
        add(lN);

        //Text Nombre
        txtN = new JTextField();
        txtN.setBounds(10, 40, 300, 30);
        add(txtN);

        //label Grupo
        lG = new JLabel("Grupo");
        lG.setBounds(10, 70, 100, 30);
        add(lG);

        //Text Grupo
        txtG = new JTextField();
        txtG.setBounds(10, 100, 300, 30);
        add(txtG);

        //boton de Registro
        botR = new JButton("Registrar");
        botR.setBounds(10, 150, 100, 30);
        add(botR);
        botR.addActionListener(this);

        //boton Modificar
        botM = new JButton("Modificar");
        botM.setBounds(120, 150, 100, 30);
        add(botM);
        botM.addActionListener(this);

        //Boton Eliminar
        botE = new JButton("Eliminar");
        botE.setBounds(240, 150, 100, 30);
        add(botE);
        botE.addActionListener(this);

        //label para Busqueda
        lB = new JLabel("Ingrese ID de Alumno:");
        lB.setBounds(10, 200, 200, 30);
        add(lB);

        //Text Id
        txtB = new JTextField();
        txtB.setBounds(210, 200, 300, 30);
        add(txtB);

        //Boton de Busqueda
        botB = new JButton("Buscar");
        botB.setBounds(10, 240, 100, 30);
        add(botB);
        botB.addActionListener(this);

        //label de estatus
        lS = new JLabel("");
        lS.setBounds(10, 280, 400, 30);
        add(lS);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botR) {
            if (txtN.getText() != "" && txtG.getText() != "") {
                try {
                    //creamos el conector
                    Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/_bd_ins", "root", "porruda.4");

                    //inicializamos la carga
                    PreparedStatement pst = cn.prepareStatement("INSERT INTO alumnos VALUES(?,?,?)");

                    //preparamos los datos a cargar y los cargamos en la base
                    pst.setString(1, null);
                    pst.setString(2, txtN.getText().trim());
                    pst.setString(3, txtG.getText().trim());
                    pst.executeUpdate();

                    //Borramos los campos
                    txtN.getText();
                    txtG.getText();

                    //Confirmamos carga
                    lS.setText("Carga Exitosa");
                } catch (Exception e1) {
                    e1.printStackTrace();
                    lS.setText("Verifique Conexion!!!!!!!!!!");
                    JOptionPane.showMessageDialog(null,"No estas conectado");
                }
            } else {
                lS.setText("Alguno de los campos se encuentra vacio");
            }
        }
        if (e.getSource() == botB) {
            if (txtB.getText() != "") {
                try {
                    //Creamos el conector
                    Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/_bd_ins", "root", "porruda.4");

                    //incializamos la carga
                    PreparedStatement pst = cn.prepareStatement("select * from alumnos where ID = ?");

                    //enviamos la consulta
                    pst.setString(1, txtB.getText().trim());

                    //Obtenemos el resultado
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        txtN.setText(rs.getString("NombreA"));
                        txtG.setText(rs.getString("Grupo"));
                    } else {
                        JOptionPane.showMessageDialog(null, "Alumno no Registrado");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,"No tiene conexion");
                }


            }


        }
        if(e.getSource() == botM){
            try{
                String ID = txtB.getText().trim();
                //creamos el conector
                Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/_bd_ins", "root", "porruda.4");

                //Modificamos el Alumno con ID ingresado
                PreparedStatement pst = cn.prepareStatement("update alumnos set NombreA = ? , Grupo = ? where ID ="+ID);

                //Usamos pst para cargar los nuevos datos
                pst.setString(1,txtN.getText().trim());
                pst.setString(2,txtG.getText().trim());
                pst.executeUpdate();

                //Notificamos al usuario
                lS.setText("Modificacion Exitosa");

                //Borramos los campos
                txtN.setText("");
                txtG.setText("");
                txtB.setText("");


            }catch (Exception e1){
                JOptionPane.showMessageDialog(null,"Error");
            }
        }
        if(e.getSource()==botE){
            try{
                //creamos el conector
                Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/_bd_ins", "root", "porruda.4");

                //inicializamos la carga
                PreparedStatement pst = cn.prepareStatement("delete from alumnos where ID =?");

                pst.setString(1,txtB.getText().trim());
                pst.executeUpdate();

                txtB.setText("");
                txtG.setText("");
                txtN.setText("");

                lS.setText("Alumno Eliminado");
                
            }catch (Exception e1){
                JOptionPane.showMessageDialog(null,"Error");
            }
        }
    }

    public static void main(String[] ar) {
        VentRegistro ven = new VentRegistro();
        ven.setBounds(0, 0, 525, 400);
        ven.setVisible(true);
    }
}


