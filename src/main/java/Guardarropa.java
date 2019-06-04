import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.math3.util.CombinatoricsUtils;
import org.paukov.combinatorics3.Generator;
import org.paukov.combinatorics3.IGenerator;

public class Guardarropa 
{
	String identificador;
	
	public Guardarropa(String identificador)
	{
		this.identificador = identificador;
	}
	
	ArrayList <Prenda> parteSuperior = new ArrayList<Prenda>();
	ArrayList <Prenda> parteInferior = new ArrayList<Prenda>();
	ArrayList <Prenda> accesorios = new ArrayList<Prenda>();
	ArrayList <Prenda> calzados = new ArrayList<Prenda>();
	
	
//-------------------AGREGAR PRENDA A GUARDARROPAS-------------------- 
	
	public void agregarAGuardarropas(Prenda prenda) throws Exception
	{	
		if(!prenda.estaEnGuardarropa()) {
			switch(prenda.getParteCuerpo()) 
			{
			case "Parte Superior":
				this.parteSuperior.add(prenda);
				prenda.meterEnGuardarropa();
				break;
			case "Parte Inferior": 
				this.parteInferior.add(prenda);
				prenda.meterEnGuardarropa();
				break;
			case "Accesorio":
				this.accesorios.add(prenda);
				prenda.meterEnGuardarropa();
				break;
			case "Calzado": 
				this.calzados.add(prenda);
				prenda.meterEnGuardarropa();
				break;
			default:
				System.out.println("ESTE ELEMENTO NO PERTENECE A NINGUNA LISTA");
				break;
			} 
		}
		else throw new Exception("PRENDA YA SE ENCUENTRA EN UN GUARDARROPA");
	}
		
//-------------------MOSTRAR LOS ARRAYS DEL GUARDARROPA-------------------- 
	
	public ArrayList<String> getArrayParteSuperior() 
	{
		ArrayList <String> arrayParteSuperior = new ArrayList <String>(this.parteSuperior.stream().map(prenda -> prenda.getTipo()).collect(Collectors.toList()));
		arrayParteSuperior.forEach(System.out::println);
		return arrayParteSuperior;
	}
	
	public ArrayList<String> getArrayParteInferior() 
	{
		ArrayList <String> arrayParteInferior = new ArrayList <String>(this.parteInferior.stream().map(prenda -> prenda.getTipo()).collect(Collectors.toList()));
		return arrayParteInferior;
	}
	
	public ArrayList<String> getArrayAccesorios() 
	{
		ArrayList <String> arrayAccesorios = new ArrayList <String>(this.accesorios.stream().map(prenda -> prenda.getTipo()).collect(Collectors.toList()));
		return arrayAccesorios;
	}
	
	public ArrayList<String> getArrayCalzado() 
	{
		ArrayList <String> arrayCalzado = new ArrayList <String>(this.calzados.stream().map(prenda -> prenda.getTipo()).collect(Collectors.toList()));
		return arrayCalzado;
	}

	

//-------------------FUNCION PRINCIPAL --------------------
		
	public Atuendo queMePongo() 
	{
		if(this.verificarArrayList()) 
		{
		    int rnd = new Random().nextInt(this.combinaciones().size());
		    List <Prenda> combinacionElegida = this.combinaciones().get(rnd);
		    Atuendo atuendoElegido = new Atuendo(combinacionElegida);
		    System.out.println("Atuendo de: " + this.identificador);
		    atuendoElegido.imprimirPrendas();
		    System.out.println("");
		    return atuendoElegido;
		}
		else
		{
			System.out.println(this.identificador + " no posee atuendos");
			return null;
		}
	}
	
	

//-------------------------Funcion de combinaciones----------------------- 

	public ArrayList<List<Prenda>> combinaciones()
	{
		IGenerator<List<Prenda>> combinaciones = Generator.cartesianProduct(this.parteSuperior, this.parteInferior, this.accesorios, this.calzados);
		ArrayList<List<Prenda>> arrayListCombinaciones = new ArrayList<List<Prenda>>();
		combinaciones.forEach(Lista->arrayListCombinaciones.add(Lista));
		return	arrayListCombinaciones;		
	}
	
//	------------------- LAS EXCEPCIONES-----------------------------------

	public void verificarGuardarropaConPrendas() throws Exception
	{
		if(parteSuperior.isEmpty()) throw new Exception("NO HAY PARTE SUPERIOR");
		if(parteInferior.isEmpty()) throw new Exception("NO HAY PARTE INFERIOR");
		if(calzados.isEmpty()) 		throw new Exception("NO HAY CALZADO");
		if(accesorios.isEmpty()) 	throw new Exception("NO HAY ACCESORIO");
	}

//-----------------------Verificacion de Array List-----------------------

	public boolean verificarArrayList() 
	{
		return this.parteSuperior.size()>0 && this.parteInferior.size()>0 && this.calzados.size()>0 && this.accesorios.size()>0 ;
	}

}