package com.rutaya.config;
import com.rutaya.model.Ruta;
import com.rutaya.repository.RutaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initRutas(RutaRepository rutaRepository) {
        return args -> {
            if (rutaRepository.count() == 0) {
                rutaRepository.save(new Ruta("Abancay","Cusco",35.0,"3h 30min","6 salidas diarias","Los Chankas Express","⛰️","#E05A1E"));
                rutaRepository.save(new Ruta("Abancay","Lima",80.0,"18h","2 salidas diarias","Cruz del Sur","🌆","#0F3460"));
                rutaRepository.save(new Ruta("Abancay","Andahuaylas",20.0,"2h","8 salidas diarias","Señor de Huanca","🏔️","#10B981"));
                rutaRepository.save(new Ruta("Cusco","Lima",95.0,"22h","3 salidas diarias","Oltursa","🚌","#7B2D8B"));
                rutaRepository.save(new Ruta("Andahuaylas","Cusco",45.0,"5h","4 salidas diarias","Los Apus","🌄","#F4A340"));
                rutaRepository.save(new Ruta("Abancay","Arequipa",65.0,"12h","2 salidas diarias","Civa","🏜️","#E24B4A"));
                System.out.println("✅ Rutas de ejemplo cargadas");
            }
        };
    }
}
