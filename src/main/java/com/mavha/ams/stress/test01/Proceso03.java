/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mavha.ams.stress.test01;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.imageio.ImageIO;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author marti
 */
@WebServlet(name = "Proceso03", urlPatterns = {"/proceso03"})
public class Proceso03 extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Proceso01</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Proceso01 at " + request.getContextPath() + "</h1>");
            out.println("<div>" + this.doAlgo(request) + "</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private String doAlgo(HttpServletRequest request) {
        Date fechaInicial = new Date();
        Random r = new Random();
        Double valorPorcentual = r.nextDouble();
        Double tiempoDormir = 1 + (valorPorcentual * 100.0);
        Long antes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        try {
            Thread.currentThread().sleep(tiempoDormir.intValue());
            gastarMemoria(request.getSession(true));
        } catch (InterruptedException ex) {
            Logger.getLogger(Proceso03.class.getName()).log(Level.SEVERE, null, ex);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        Date fechaFinal = new Date();
        Long duracion = fechaFinal.getTime() - fechaInicial.getTime();
        Long despues = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        JsonObject objeto = Json.createObjectBuilder().
                add("inicio", sdf.format(fechaInicial)).
                add("fin", sdf.format(fechaFinal)).
                add("porcentual", valorPorcentual).
                add("duracion", duracion).
                add("sleep", tiempoDormir).
                add("memoriaAntes", antes).
                add("memoriaDespues", despues).
                add("diffConsumo", (despues - antes)).
                build();
        return objeto.toString();
    }

    private void gastarMemoria(HttpSession sesion) {     //image dimension
        for (int i = 1; i < 200; i++) {
            sesion.setAttribute("attAA" + i, UUID.randomUUID().toString());
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");
                String s2 = UUID.randomUUID().toString();
                String dato = new String(md.digest(s2.getBytes()));
                sesion.setAttribute("attBB" + i, dato);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Proceso03.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
