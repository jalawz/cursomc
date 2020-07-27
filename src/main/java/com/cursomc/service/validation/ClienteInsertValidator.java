package com.cursomc.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.dto.ClienteNewDTO;
import com.cursomc.resources.exception.FieldMessage;
import com.cursomc.service.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Override
    public void initialize (final ClienteInsert constraintAnnotation) {

    }

    @Override
    public boolean isValid (final ClienteNewDTO value, final ConstraintValidatorContext context) {
        final List<FieldMessage> list = new ArrayList<>();

        if (value.getTipo().equals(TipoCliente.PESSOA_FISICA.getCod()) && !BR.isValidCPF(value.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF Invalido"));
        }

        if (value.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCod()) && !BR.isValidCPF(value.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ Invalido"));
        }

        list.forEach(field -> {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(field.getFielMessage()).addPropertyNode(field.getFieldName())
                    .addConstraintViolation();
        });
        return list.isEmpty();
    }
}
