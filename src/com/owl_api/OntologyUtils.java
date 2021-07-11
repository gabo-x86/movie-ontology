package com.owl_api;
    
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

public class OntologyUtils {
    private OWLOntologyManager man;//Factoria que crea instancia de ontología
    private IRI ontologyIRI;//Identificador de Recursos Internacional
    private OWLOntology ontology;//Abstracción de ontología en memoria
    private File ontologyFile;//Archivo de cargado de la ontología
    
    public OntologyUtils(String path) throws OWLOntologyCreationException{
        this.ontologyFile=new File(path);
        this.man=OWLManager.createOWLOntologyManager();
        this.ontology=man.loadOntologyFromOntologyDocument(ontologyFile);
        this.ontologyIRI=ontology.getOntologyID().getOntologyIRI();
        System.out.println("Ontología cargada!!!");
    }
    
    public void loadPersons(String path) throws FileNotFoundException, IOException, OWLOntologyStorageException{        
        System.out.println("CARGANDO PERSONAS...");
        BufferedReader csvReader = new BufferedReader(new FileReader(path));        
        String row;
        int i=0;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            //Clase
            OWLDataFactory factory=man.getOWLDataFactory();
            OWLClass clasePersona=factory.getOWLClass(ontologyIRI.resolve(ontologyIRI+"#Persona"));        
            OWLNamedIndividual individuoPersona=factory.getOWLNamedIndividual(ontologyIRI.resolve(ontologyIRI+"#"+data[0]));        
            OWLClassAssertionAxiom classAsertion=factory.getOWLClassAssertionAxiom(clasePersona, individuoPersona);//Asociamos individual a la Clase
            man.addAxiom(ontology, classAsertion);//Agregamos axioma de clase a la ontología
            
            //Crear propiedad de individual de Clase
            //ID
            OWLDataProperty propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#id_persona"));
            OWLLiteral literalPropiedad=factory.getOWLTypedLiteral(data[0]);//Valor de la propiedad
            OWLDataPropertyAssertionAxiom propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPersona, literalPropiedad);
            man.addAxiom(ontology, propiedad);            
            //Nombre persona
            propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#nombre_persona"));
            literalPropiedad=factory.getOWLTypedLiteral(data[2]);//Valor de la propiedad
            propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPersona, literalPropiedad);
            man.addAxiom(ontology, propiedad);
            //Fecha de Nacimiento            
            propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#fecha_nacimiento"));
            literalPropiedad=factory.getOWLTypedLiteral(data[3]+"T00:00:00", OWL2Datatype.XSD_DATE_TIME);//Valor de la propiedad            
            propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPersona, literalPropiedad);
            man.addAxiom(ontology, propiedad);
            //Lugar de nacimiento
            propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#pais_nacimiento"));
            literalPropiedad=factory.getOWLTypedLiteral(data[4]);//Valor de la propiedad
            propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPersona, literalPropiedad);
            man.addAxiom(ontology, propiedad);            
            //--
            System.out.println(i+".- "+data[0]);
            i++;
        }
        csvReader.close();
        
        //saveOntology();
        
    }
    
    public void loadAwards(String path) throws FileNotFoundException, IOException, OWLOntologyStorageException{
        System.out.println("CARGANDO PREMIOS...");
        BufferedReader csvReader = new BufferedReader(new FileReader(path));        
        String row;
        int i=0;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            //Clase
            OWLDataFactory factory=man.getOWLDataFactory();
            OWLClass clasePremio=factory.getOWLClass(ontologyIRI.resolve(ontologyIRI+"#Premio"));        
            OWLNamedIndividual individuoPremio=factory.getOWLNamedIndividual(ontologyIRI.resolve(ontologyIRI+"#"+data[0]));        
            OWLClassAssertionAxiom classAsertion=factory.getOWLClassAssertionAxiom(clasePremio, individuoPremio);//Asociamos individual a la Clase
            man.addAxiom(ontology, classAsertion);//Agregamos axioma de clase a la ontología
            
            //Crear propiedad de individual de Clase
            //ID
            OWLDataProperty propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#id_premio"));
            OWLLiteral literalPropiedad=factory.getOWLTypedLiteral(data[0]);//Valor de la propiedad
            OWLDataPropertyAssertionAxiom propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPremio, literalPropiedad);
            man.addAxiom(ontology, propiedad);            
            //Tipo de Premio
            propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#tipo_premio"));
            literalPropiedad=factory.getOWLTypedLiteral(data[1]);//Valor de la propiedad
            propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPremio, literalPropiedad);
            man.addAxiom(ontology, propiedad);
            //Año de entrega de Premio
            propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#año_premio"));
            literalPropiedad=factory.getOWLTypedLiteral(data[2], OWL2Datatype.XSD_POSITIVE_INTEGER);//Valor de la propiedad            
            propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPremio, literalPropiedad);
            man.addAxiom(ontology, propiedad);
            //Motivo de Premio
            propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#motivo_premio"));
            literalPropiedad=factory.getOWLTypedLiteral(data[3]);//Valor de la propiedad
            propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPremio, literalPropiedad);
            man.addAxiom(ontology, propiedad);            
            //--
            System.out.println(i+".- "+data[0]);
            i++;
        }
        csvReader.close();
        
        //saveOntology();
    }
    
    public void loadMovies(String path) throws FileNotFoundException, IOException, OWLOntologyStorageException{
        System.out.println("CARGANDO PELÍCULAS...");
        BufferedReader csvReader = new BufferedReader(new FileReader(path));        
        String row;
        int i=0;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            //Clase
            OWLDataFactory factory=man.getOWLDataFactory();
            OWLClass clasePremio=factory.getOWLClass(ontologyIRI.resolve(ontologyIRI+"#Pelicula"));        
            OWLNamedIndividual individuoPremio=factory.getOWLNamedIndividual(ontologyIRI.resolve(ontologyIRI+"#"+data[0]));        
            OWLClassAssertionAxiom classAsertion=factory.getOWLClassAssertionAxiom(clasePremio, individuoPremio);//Asociamos individual a la Clase
            man.addAxiom(ontology, classAsertion);//Agregamos axioma de clase a la ontología
            
            //Crear propiedad de individual de Clase
            //ID
            OWLDataProperty propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#id_pelicula"));
            OWLLiteral literalPropiedad=factory.getOWLTypedLiteral(data[0]);//Valor de la propiedad
            OWLDataPropertyAssertionAxiom propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPremio, literalPropiedad);
            man.addAxiom(ontology, propiedad);            
            //Nombre de película
            propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#nombre_pelicula"));
            literalPropiedad=factory.getOWLTypedLiteral(data[1]);//Valor de la propiedad
            propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPremio, literalPropiedad);
            man.addAxiom(ontology, propiedad);
            //Descripción de película            
            propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#descripcion_pelicula"));
            literalPropiedad=factory.getOWLTypedLiteral(data[2]);//Valor de la propiedad
            propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPremio, literalPropiedad);
            man.addAxiom(ontology, propiedad);
            //Género película
            propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#nombre_genero"));
            literalPropiedad=factory.getOWLTypedLiteral(data[3]);//Valor de la propiedad
            propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPremio, literalPropiedad);
            man.addAxiom(ontology, propiedad); 
            //Fecha de estreno
            propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#fecha_estreno"));
            literalPropiedad=factory.getOWLTypedLiteral(data[4]+"T00:00:00", OWL2Datatype.XSD_DATE_TIME);//Valor de la propiedad            
            propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPremio, literalPropiedad);
            man.addAxiom(ontology, propiedad); 
            //País de origen
            propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#pais_pelicula"));
            literalPropiedad=factory.getOWLTypedLiteral(data[5]);//Valor de la propiedad
            propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPremio, literalPropiedad);
            man.addAxiom(ontology, propiedad);            
            //Idioma película
            propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#idioma_original"));
            literalPropiedad=factory.getOWLTypedLiteral(data[6]);//Valor de la propiedad
            propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPremio, literalPropiedad);
            man.addAxiom(ontology, propiedad);            
            //Duración película
            propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#duracion_pelicula"));
            literalPropiedad=factory.getOWLTypedLiteral(data[7],OWL2Datatype.XSD_POSITIVE_INTEGER);//Valor de la propiedad
            propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPremio, literalPropiedad);
            man.addAxiom(ontology, propiedad);            
            //Productora película
            propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#productor_pelicula"));
            literalPropiedad=factory.getOWLTypedLiteral(data[8]);//Valor de la propiedad
            propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPremio, literalPropiedad);
            man.addAxiom(ontology, propiedad);            
            
            //--
            System.out.println(i+".- "+data[0]);
            i++;
        }
        csvReader.close();
        
        //saveOntology();
    }
    
    public void loadQualifications(String path) throws FileNotFoundException, IOException, OWLOntologyStorageException{
        System.out.println("CARGANDO CALIFICACIONES...");
        BufferedReader csvReader = new BufferedReader(new FileReader(path));        
        String row;
        int i=0;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            //Clase
            OWLDataFactory factory=man.getOWLDataFactory();
            OWLClass clasePersona=factory.getOWLClass(ontologyIRI.resolve(ontologyIRI+"#Calificacion"));        
            OWLNamedIndividual individuoPersona=factory.getOWLNamedIndividual(ontologyIRI.resolve(ontologyIRI+"#"+data[0]));        
            OWLClassAssertionAxiom classAsertion=factory.getOWLClassAssertionAxiom(clasePersona, individuoPersona);//Asociamos individual a la Clase
            man.addAxiom(ontology, classAsertion);//Agregamos axioma de clase a la ontología
            
            //Crear propiedad de individual de Clase
            //ID
            OWLDataProperty propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#id_calificacion"));
            OWLLiteral literalPropiedad=factory.getOWLTypedLiteral(data[0]);//Valor de la propiedad
            OWLDataPropertyAssertionAxiom propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPersona, literalPropiedad);
            man.addAxiom(ontology, propiedad);
            //Fecha de Nacimiento            
            propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#calificacion"));
            literalPropiedad=factory.getOWLTypedLiteral(data[1], OWL2Datatype.XSD_FLOAT);//Valor de la propiedad            
            propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPersona, literalPropiedad);
            man.addAxiom(ontology, propiedad);
            //Fecha de Nacimiento            
            propiedadIRI=factory.getOWLDataProperty(ontologyIRI.resolve(ontologyIRI+"#numero_votos"));
            literalPropiedad=factory.getOWLTypedLiteral(data[2], OWL2Datatype.XSD_POSITIVE_INTEGER);//Valor de la propiedad            
            propiedad=factory.getOWLDataPropertyAssertionAxiom(propiedadIRI, individuoPersona, literalPropiedad);
            man.addAxiom(ontology, propiedad);
          
            //--
            System.out.println(i+".- "+data[0]);
            i++;
        }
        csvReader.close();
        
        //saveOntology();    
    }
    
    public void loadMovieScoreRelation(String path) throws FileNotFoundException, IOException{
        System.out.println("CARGANDO RELACION Pelicula-Calificacion");
        BufferedReader csvReader = new BufferedReader(new FileReader(path));        
        String row;
        int i=0;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            // do something with the data
            OWLDataFactory factory=man.getOWLDataFactory();
            OWLNamedIndividual individuoCalificacion=factory.getOWLNamedIndividual(ontologyIRI.resolve(ontologyIRI+"#"+data[1]));//Nombre del Individual  
            OWLNamedIndividual individuoPelicula=factory.getOWLNamedIndividual(ontologyIRI.resolve(ontologyIRI+"#"+data[0]));  
            OWLObjectProperty tieneCalificacion=factory.getOWLObjectProperty(ontologyIRI.resolve(ontologyIRI+"#tiene_calificacion"));
            OWLObjectPropertyAssertionAxiom relPersPel=factory.getOWLObjectPropertyAssertionAxiom(tieneCalificacion, individuoPelicula, individuoCalificacion);
            man.addAxiom(ontology, relPersPel);
            
            
            System.out.println(i+".- "+data[0]);
            i++;
        }
        csvReader.close();
    }
     
    public void loadMovieActorRelation(String path) throws FileNotFoundException, IOException{
        System.out.println("CARGANDO RELACION Actor-Pelicula");
        BufferedReader csvReader = new BufferedReader(new FileReader(path));        
        String row;
        int i=0;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            // do something with the data
            OWLDataFactory factory=man.getOWLDataFactory();
            OWLNamedIndividual individuoActor=factory.getOWLNamedIndividual(ontologyIRI.resolve(ontologyIRI+"#"+data[1]));//Nombre del Individual  
            OWLNamedIndividual individuoPelicula=factory.getOWLNamedIndividual(ontologyIRI.resolve(ontologyIRI+"#"+data[0]));  
            OWLObjectProperty protagonizadaPor=factory.getOWLObjectProperty(ontologyIRI.resolve(ontologyIRI+"#protagonizada_por"));
            OWLObjectPropertyAssertionAxiom relPersPel=factory.getOWLObjectPropertyAssertionAxiom(protagonizadaPor, individuoPelicula, individuoActor);
            man.addAxiom(ontology, relPersPel);
            
            
            System.out.println(i+".- "+data[0]);
            i++;
        }
        csvReader.close();
    }
    
    public void loadMovieDirectorRelation(String path) throws FileNotFoundException, IOException{
        System.out.println("CARGANDO RELACION Director-Pelicula");
        BufferedReader csvReader = new BufferedReader(new FileReader(path));        
        String row;
        int i=0;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            // do something with the data
            OWLDataFactory factory=man.getOWLDataFactory();
            OWLNamedIndividual individuoDirector=factory.getOWLNamedIndividual(ontologyIRI.resolve(ontologyIRI+"#"+data[1]));//Nombre del Individual  
            OWLNamedIndividual individuoPelicula=factory.getOWLNamedIndividual(ontologyIRI.resolve(ontologyIRI+"#"+data[0]));  
            OWLObjectProperty dirigidaPor=factory.getOWLObjectProperty(ontologyIRI.resolve(ontologyIRI+"#dirigida_por"));
            OWLObjectPropertyAssertionAxiom relPersPel=factory.getOWLObjectPropertyAssertionAxiom(dirigidaPor, individuoPelicula, individuoDirector);
            man.addAxiom(ontology, relPersPel);
            
            
            System.out.println(i+".- "+data[0]);
            i++;
        }
        csvReader.close();
    }
    
    public void loadMovieWriterRelation(String path) throws FileNotFoundException, IOException{
        System.out.println("CARGANDO RELACION Escritor-Pelicula");
        BufferedReader csvReader = new BufferedReader(new FileReader(path));        
        String row;
        int i=0;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            // do something with the data
            OWLDataFactory factory=man.getOWLDataFactory();
            OWLNamedIndividual individuoEscritor=factory.getOWLNamedIndividual(ontologyIRI.resolve(ontologyIRI+"#"+data[1]));//Nombre del Individual  
            OWLNamedIndividual individuoPelicula=factory.getOWLNamedIndividual(ontologyIRI.resolve(ontologyIRI+"#"+data[0]));  
            OWLObjectProperty escritaPor=factory.getOWLObjectProperty(ontologyIRI.resolve(ontologyIRI+"#escrita_por"));
            OWLObjectPropertyAssertionAxiom relPersPel=factory.getOWLObjectPropertyAssertionAxiom(escritaPor, individuoPelicula, individuoEscritor);
            man.addAxiom(ontology, relPersPel);
            
            
            System.out.println(i+".- "+data[0]);
            i++;
        }
        csvReader.close();
    }
    
    public void loadMoviePersonAwardRelation(String path) throws FileNotFoundException, IOException{
        System.out.println("CARGANDO RELACION Persona o Pelicula-Premio");
        BufferedReader csvReader = new BufferedReader(new FileReader(path));        
        String row;
        int i=0;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            // do something with the data
            OWLDataFactory factory=man.getOWLDataFactory();
            OWLNamedIndividual individuoPremio=factory.getOWLNamedIndividual(ontologyIRI.resolve(ontologyIRI+"#"+data[1]));//Nombre del Individual  
            OWLNamedIndividual individuo=factory.getOWLNamedIndividual(ontologyIRI.resolve(ontologyIRI+"#"+data[0]));  
            
            OWLObjectProperty gana;
            if(data[0].charAt(0)=='t'){
                gana=factory.getOWLObjectProperty(ontologyIRI.resolve(ontologyIRI+"#pelicula_gana"));
            }else{
                gana=factory.getOWLObjectProperty(ontologyIRI.resolve(ontologyIRI+"#persona_gana"));
            }            
            
            OWLObjectPropertyAssertionAxiom relPersPel=factory.getOWLObjectPropertyAssertionAxiom(gana, individuo, individuoPremio);
            man.addAxiom(ontology, relPersPel);
            
            
            System.out.println(i+".- "+data[0]);
            i++;
        }
        csvReader.close();
    }
    
    public void saveOntology() throws OWLOntologyStorageException{
        this.man.saveOntology(this.ontology, new RDFXMLOntologyFormat(), this.ontologyIRI.resolve(this.ontologyFile.toURI().toString()));
        //this.man.saveOntology(this.ontology, new OWLXMLOntologyFormat(), this.ontologyIRI.resolve(this.ontologyFile.toURI().toString()));
    }
    
}
