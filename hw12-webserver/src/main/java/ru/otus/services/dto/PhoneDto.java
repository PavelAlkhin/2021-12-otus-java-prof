package ru.otus.services.dto;

import ru.otus.crm.model.Phone;

import java.util.ArrayList;
import java.util.List;

public class PhoneDto {
    private Long id;
    private String number;

    public PhoneDto(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public static PhoneDto create(Phone phone){
        return new PhoneDto(phone.getId(), phone.getNumber());
    }

    public static List<PhoneDto> createList(List<Phone> phones){
        List<PhoneDto> lstDto = new ArrayList<>();
        phones.forEach(p -> lstDto.add(PhoneDto.create(p)));
        return lstDto;
    }
}
