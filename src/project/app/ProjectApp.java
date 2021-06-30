package project.app;

import project.rest.CampeonatoResource;
import project.rest.TimeResource;
import project.dao.CampeonatoDao;
import project.dao.TimeDao;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.assets.AssetsBundle;
import project.conexao.ConexaoJavaDb;

public class ProjectApp extends Application<Configuration> {
    public static void main(String[] args) throws Exception {
        new ProjectApp().run(new String[] { "server" });
    }

    @Override
    public void initialize(final Bootstrap<Configuration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/resources/html", "/site", "indexTimeCampeonato.html"));
    }
    
    @Override
    public void run(Configuration configuration, Environment environment) {
        try {
            ConexaoJavaDb conexao;
            conexao = new ConexaoJavaDb("app", "app", "localhost", 1527, "TimeCampeonato");
            CampeonatoDao campeonatoDao = new CampeonatoDao(conexao);
            TimeDao timeDao = new TimeDao(conexao);
            environment.jersey().register(new CampeonatoResource(campeonatoDao));
            environment.jersey().register(new TimeResource(timeDao)); 
            environment.jersey().setUrlPattern("/api/*");
        } catch(Exception e) {
            e.printStackTrace();
        }           
    }
}