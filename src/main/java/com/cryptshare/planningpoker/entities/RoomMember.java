package com.cryptshare.planningpoker.entities;

import jakarta.persistence.*;

import java.util.StringJoiner;

@Entity
@Table(name = "room_member")
public class RoomMember extends BaseEntity {
	public enum Role {
		MODERATOR,
		USER,
		OBSERVER
	}

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "user_role", nullable = false)
	private Role role;

	protected RoomMember() {
	}

	public RoomMember(User user, Role role) {
		this.user = user;
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", RoomMember.class.getSimpleName() + "[", "]").add("user=" + user).add("role=" + role).toString();
	}
}
