package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modelo.User;
import modelo.Carta;
import modelo.Partida;
import vista.VistaSuitcards;
import vista.VistaLogin;
import vista.VistaBienvenida;
import vista.VistaRegistroUsuario;
import vista.VistaBlackjack;
import vista.VistaLoadCards;
import vista.VistaElegirTurno;

/**
 * Clase Controlador que gestiona la lógica de navegación y control del juego de Blackjack.
 */
public class Controlador {
    private VistaBienvenida vistaBienvenida;
    private VistaLogin vistaLogin;
    private VistaLoadCards vistaLoadCards;
    private VistaSuitcards vistaSuitcards;
    private VistaRegistroUsuario vistaRegistroUsuario;
    private VistaBlackjack vistaBlackjack;
    private VistaElegirTurno vistaElegirTurno;
    private String tipoBarajaSeleccionada;
    private String turnoJugador;

    /**
     * Constructor de la clase Controlador.
     * Inicia la pantalla de bienvenida.
     */
    public Controlador() {
        iniciarBienvenida();
    }

    /**
     * Inicia la vista de bienvenida y asigna acciones a los botones de registro e inicio de sesión.
     */
    private void iniciarBienvenida() {
        vistaBienvenida = new VistaBienvenida();
        User.primeraConexion();
        User.primerUsuario();
        
        vistaBienvenida.getRegistroButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vistaBienvenida.dispose();
                iniciarRegistroUsuario();
            }
        });

        vistaBienvenida.getLoginButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vistaBienvenida.dispose();
                iniciarLogin();
            }
        });
    }

    /**
     * Inicia la vista de login y gestiona la autenticación del usuario.
     */
    private void iniciarLogin() {
        vistaLogin = new VistaLogin();
        vistaLogin.getLoginButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = vistaLogin.userText.getText();
                String password = new String(vistaLogin.passwordText.getPassword());
                String confirmPassword = new String(vistaLogin.confirmPasswordText.getPassword());

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(vistaLogin, "Las contraseñas no coinciden.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (User.verificarUsuario(username, password)) {
                    JOptionPane.showMessageDialog(vistaLogin, "¡Login exitoso!");
                    vistaLogin.dispose();
                    iniciarImportacionCards();
                } else {
                    JOptionPane.showMessageDialog(vistaLogin, "Usuario o contraseña incorrectos.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Inicia la vista para importar cartas desde MongoDB.
     */
    private void iniciarImportacionCards() {
        vistaLoadCards = new VistaLoadCards();
        vistaLoadCards.getLoadCardsButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Carta.cargarImagenesEnMongo();
                    JOptionPane.showMessageDialog(vistaLoadCards, "Importación completada con éxito.");
                    vistaLoadCards.dispose();
                    iniciarCargaTablero();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(vistaLoadCards,
                            "Error en la importación de cartas: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Inicia la vista de registro de usuario y gestiona la creación de un nuevo usuario.
     */
    private void iniciarRegistroUsuario() {
        vistaRegistroUsuario = new VistaRegistroUsuario();
        vistaRegistroUsuario.getRegisterButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String username = vistaRegistroUsuario.userText.getText();
                String password = new String(vistaRegistroUsuario.passwordText.getPassword());
                String confirmPassword = new String(vistaRegistroUsuario.confirmPasswordText.getPassword());

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(vistaRegistroUsuario, "Las contraseñas no coinciden.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (User.registrarUsuario(username, password)) {
                        JOptionPane.showMessageDialog(vistaRegistroUsuario, "Usuario registrado con éxito.");
                        vistaRegistroUsuario.dispose();
                        iniciarLogin();
                    } else {
                        JOptionPane.showMessageDialog(vistaRegistroUsuario, "Error al registrar el usuario.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    /**
     * Inicia la vista del juego de Blackjack y gestiona los eventos de los botones.
     */
    private void iniciarCargaTablero() {
        vistaBlackjack = new VistaBlackjack();
        vistaBlackjack.getBtnStart().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                elegirSuit();
            }
        });

        vistaBlackjack.getBtnJugar().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                //FALTA ALGORITMO DEL JUEGO
            }
        });

        vistaBlackjack.getBtnLogout().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                iniciarBienvenida();
            }
        });
    }
    
    /**
     * Inicia la vista de selección de tipo de baraja y obtiene la elección del usuario.
     */
    private void elegirSuit() {
        vistaSuitcards = new VistaSuitcards();
        vistaSuitcards.getCardsESButton1().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tipoBarajaSeleccionada = "cards_es";
                System.out.println("Se ha seleccionado la baraja española.");
                vistaSuitcards.dispose();
            }
        });

        vistaSuitcards.getCardsFRButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tipoBarajaSeleccionada = "cards_fr";
                System.out.println("Se ha seleccionado la baraja francesa.");
                vistaSuitcards.dispose();
            }
        });

        Partida.obtenerCartasDeMongo(tipoBarajaSeleccionada);
        elegirTurno();
    }

    /**
     * Inicia la vista de selección de turno y obtiene la elección del usuario.
     */
    private void elegirTurno() {
        vistaElegirTurno = new VistaElegirTurno();
        vistaElegirTurno.getCrupierButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                turnoJugador = "crupier";
                vistaElegirTurno.dispose();
            }
        });

        vistaElegirTurno.getPlayerButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                turnoJugador = "player";
                vistaElegirTurno.dispose();
            }
        });
    }
}
