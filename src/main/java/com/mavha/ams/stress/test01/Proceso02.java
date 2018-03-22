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
import javax.imageio.ImageIO;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author marti
 */
@WebServlet(name = "Proceso02", urlPatterns = {"/proceso02"})
public class Proceso02 extends HttpServlet {

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
            out.println("<div>"+this.doAlgo()+"</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private String doAlgo() {
        Date fechaInicial = new Date();
        Random r = new Random();
        Double valorPorcentual = r.nextDouble();
        Double tiempoDormir = 1 +(valorPorcentual  * 100.0);
        try {
            Thread.currentThread().sleep(tiempoDormir.intValue());
            generarImagen("img_"+System.currentTimeMillis());
        } catch (InterruptedException ex) {
            Logger.getLogger(Proceso02.class.getName()).log(Level.SEVERE, null, ex);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        Date fechaFinal = new Date();
        Long duracion = fechaFinal.getTime() - fechaInicial.getTime();
        JsonObject objeto = Json.createObjectBuilder().
                add("inicio", sdf.format(fechaInicial)).
                add("fin", sdf.format(fechaFinal)).
                add("porcentual",valorPorcentual).
                add("duracion", duracion).
                add("sleep", tiempoDormir).
                build();
        return objeto.toString();
    }


private void generarImagen(String nombre){     //image dimension
     int width = 640;
     int height = 320;
     //create buffered image object img
     BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
     //file object
     File f = null;
     //create random image pixel by pixel
     for(int y = 0; y < height; y++){
       for(int x = 0; x < width; x++){
         int a = (int)(Math.random()*256); //alpha
         int r = (int)(Math.random()*256); //red
         int g = (int)(Math.random()*256); //green
         int b = (int)(Math.random()*256); //blue
 
         int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel
 
         img.setRGB(x, y, p);
       }
     }
     //write image
     try{
       f = new File("C:/Users/marti/Pictures/test/"+nombre+".png");
       ImageIO.write(img, "png", f);
     }catch(IOException e){
         e.printStackTrace();
     }
  }//main() ends here
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
