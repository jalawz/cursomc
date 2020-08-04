package com.cursomc.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cursomc.domain.Cliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.repository.ClienteRepository;
import com.cursomc.resources.exception.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize (final ClienteUpdate constraintAnnotation) {

    }

    @Override
    public boolean isValid (final ClienteDTO value, final ConstraintValidatorContext context) {
        final Map<String, String> uriVars = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        final Integer uriId = Integer.valueOf(uriVars.get("id"));

        final List<FieldMessage> list = new ArrayList<>();

        final Cliente cliente = clienteRepository.findByEmail(value.getEmail());

        if (!Objects.isNull(cliente) && !cliente.getId().equals(uriId)) {
            list.add(new FieldMessage("email", "Email ja existente"));
        }

        list.forEach(field -> {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(field.getFielMessage()).addPropertyNode(field.getFieldName())
                    .addConstraintViolation();
        });
        return list.isEmpty();
    }
}
