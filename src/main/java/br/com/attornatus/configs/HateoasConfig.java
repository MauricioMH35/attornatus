package br.com.attornatus.configs;

import br.com.attornatus.resources.hateoas.assemblers.AddressHateoasAssembler;
import br.com.attornatus.resources.hateoas.assemblers.PersonHateoasAssembler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HateoasConfig {

    @Bean
    public PersonHateoasAssembler personHateoasAssembler() {
        return new PersonHateoasAssembler();
    }

    @Bean
    public AddressHateoasAssembler addressHateoasAssembler() {
        return new AddressHateoasAssembler();
    }

}
