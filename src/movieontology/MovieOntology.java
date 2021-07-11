package movieontology;

import com.owl_api.OntologyUtils;
import com.owl.explorer.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class MovieOntology {

    public static void main(String[] args) throws IOException, ParseException, OWLOntologyCreationException, FileNotFoundException, OWLOntologyStorageException {

        /*OntologyUtils ontologyUtils=new OntologyUtils("ontology/MovieOntology.owl");
        ontologyUtils.loadPersons("dataset/ok/personas.csv");
        ontologyUtils.loadAwards("dataset/ok/premios.csv");
        ontologyUtils.loadMovies("dataset/ok/peliculas.csv");
        ontologyUtils.loadQualifications("dataset/ok/calificaciones.csv");
        ontologyUtils.loadMovieScoreRelation("dataset/ok/relaciones_Pelicula-Calificacion.csv");
        ontologyUtils.loadMovieActorRelation("dataset/ok/relaciones_Actor-Pelicula.csv");
        ontologyUtils.loadMovieDirectorRelation("dataset/ok/relaciones_Director-Pelicula.csv");
        ontologyUtils.loadMovieWriterRelation("dataset/ok/relaciones_Escritor-Pelicula.csv");        
        ontologyUtils.loadMoviePersonAwardRelation("dataset/ok/relaciones_PersonaPelicula-Premio.csv");        
        ontologyUtils.saveOntology();*/
        
        MainView mainView = new MainView();
        mainView.setVisible(true);
        
        
                
        /*System.out.println(convDate("1900-01-01"));        
        BufferedReader csvReader = new BufferedReader(new FileReader("dataset/ok/relaciones_PersonaPelicula-Premio.csv"));        
        String row;
        int i=0;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            // do something with the data
            if(data[0].charAt(0)=='t'){
                System.out.println("ES 'tt'");
            }else System.out.println("ES 'nm'");
            //System.out.println(i+".- "+data[0]+" / "+data[1]);
            i++;
        }
        csvReader.close();*/
    }
    
    public static String convDate(String date) throws ParseException{
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = newDateFormat.parse(date);
        newDateFormat.applyPattern("yyyy-MM-dd");
        String Resdate = newDateFormat.format(myDate)+"T00:00:00";
        return Resdate;
    }
    
}
