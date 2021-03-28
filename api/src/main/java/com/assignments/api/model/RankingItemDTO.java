package com.assignments.api.model;

public class RankingItemDTO {

    private final String login;
    private final Integer points;

    public RankingItemDTO(String login, Integer points) {
        this.login = login;
        this.points = points;
    }

    public String getLogin() {
        return login;
    }

    public Integer getPoints() {
        return points;
    }
}
