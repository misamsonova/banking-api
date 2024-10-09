package com.example.bankingapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateAccountRequest {

    @NotBlank(message = "Имя владельца не должно быть пустым.")
    private String ownerName;

    @NotBlank(message = "PIN-код не должен быть пустым.")
    @Pattern(regexp = "\\d{4}", message = "PIN-код должен состоять из 4 цифр.")
    private String pin;


    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
