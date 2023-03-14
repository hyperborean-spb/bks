package com.bks.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name  = "email_data")
@Setter
@Getter
@NoArgsConstructor
public class Mail {

	@Id
	private Long id;

	@ManyToOne  (fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Client user;

	@Email
	private  String email;
}