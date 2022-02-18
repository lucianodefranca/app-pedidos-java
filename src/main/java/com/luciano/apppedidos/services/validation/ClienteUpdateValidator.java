package com.luciano.apppedidos.services.validation;

import com.luciano.apppedidos.entities.Cliente;
import com.luciano.apppedidos.entities.dtos.ClienteDTO;
import com.luciano.apppedidos.entities.dtos.ClienteNewDTO;
import com.luciano.apppedidos.entities.enums.TipoCliente;
import com.luciano.apppedidos.repositories.ClienteRepository;
import com.luciano.apppedidos.resources.exceptions.FieldMessage;
import com.luciano.apppedidos.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ClienteRepository repository;

    @Override
    public void initialize(ClienteUpdate ann) {
    }
    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Cliente aux = repository.findByEmail(objDto.getEmail());
        if (aux != null && !aux.getId().equals(uriId)) {
            list.add(new FieldMessage("email", "Email ja cadastrado na base de dados!"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
