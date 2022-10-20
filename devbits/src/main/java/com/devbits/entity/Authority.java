package com.devbits.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authority", catalog = "jwt_db")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements java.io.Serializable {

	private static final long serialVersionUID = 8191996916769923230L;

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "username", column = @Column(name = "username", nullable = false)),
			@AttributeOverride(name = "role", column = @Column(name = "role", nullable = false)) })
	private AuthorityId id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username", nullable = false, insertable = false, updatable = false)
	private User user;



}

