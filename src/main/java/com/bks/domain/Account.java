package com.bks.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Account {

	@Id
	private Long id;

	private BigDecimal balance;

	@OneToOne  (fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
}