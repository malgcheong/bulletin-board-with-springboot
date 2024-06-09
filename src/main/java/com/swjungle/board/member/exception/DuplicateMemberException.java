package com.swjungle.board.member.exception;

public class DuplicateMemberException extends RuntimeException {
    public DuplicateMemberException(String userName) {
        super("Username already in use: " + userName);
    }
}
