package com.bank.accountservice.entities;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateBalanceRequest {

    @NotNull(message = "New balance amount is required")
    private BigDecimal newBalance;

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

	/*
	 * public UpdateBalanceRequest(@NotNull(message =
	 * "New balance amount is required") BigDecimal newBalance) { super();
	 * this.newBalance = newBalance; }
	 */
}
