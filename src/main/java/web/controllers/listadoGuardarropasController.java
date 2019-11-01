package web.controllers;

import java.util.ArrayList;
import java.util.List;

import TPZTBCS.Guardarropa;
import TPZTBCS.Usuario;
import TPZTBCS.dao.GuardarropaDao;
import TPZTBCS.dao.UsuarioDao;
import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import web.Router;
import web.models.AlertModel;
import web.models.listadoGuardarropaModel;
import web.models.views.listadoGuardarropaTable;
import spark.Response;

import org.apache.commons.math3.optim.PointValuePair;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;


public class listadoGuardarropasController extends MainController {

	
	public static PointValuePair solucion;
	private static final String HOME = "/listadoGuardarropas.hbs";
	private static listadoGuardarropaModel model;
    private static Usuario currentUser;
    private static AlertModel alert = new AlertModel(false,"",false);
    
    private static UsuarioDao uDao = new UsuarioDao();
    private static GuardarropaDao gDao = new GuardarropaDao();
    
	
	 
    public static void init() {
        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
        Spark.get(Router.homePath(), listadoGuardarropasController::showListado, engine);
        initModel();
        
    }

    private static ModelAndView showListado(Request request, Response response) {
        sessionExist(request, response);

        String userSession = request.session().attribute("user");
        Integer userID = Integer.parseInt(userSession.substring(0, userSession.indexOf("-")));
        
        currentUser = uDao.getUsuario(userID);
        

        
        try
            {
                alert.setHideAlert();
                fillListadoGuardarropasTable();
                
                return new spark.ModelAndView(model,HOME);
            }
        catch(Exception e)
            {
                alert.setShowAlertWithMessage("Errorr!!!");
                return new ModelAndView(alert, HOME);
            }

        }

    private static void initModel() {

        model = new listadoGuardarropaModel();
    }

   private static void fillListadoGuardarropasTable() {

        List<listadoGuardarropaTable> table = new ArrayList<listadoGuardarropaTable>();
        List<Guardarropa> guardarropas = gDao.getAllGuardarropas(currentUser);

        double indice = 0;
        
       try{
	       for(int i = 0; i < 2;i++)
	       {
	           indice = (solucion.getPoint()[i]);
	          
	           listadoGuardarropaTable row = new listadoGuardarropaTable();
	           row.setGuardarropa(guardarropas.get(i).getIdentificador());
	           row.setIndex((int) indice);
	           table.add(row);
	       }
       }
       catch(Exception e){}

       model.setlistadoGuardarropaTable(table);
    }
   

}
