package com.example.demo.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    User setUpUser;

    @BeforeEach
    void setUp() {
        setUpUser = userService.save("set-up-nickname", 20);
    }

    @Test
    @DisplayName("Non Transactional")
    @Transactional
    void changeNicknameTest() {
        String nickname = "sample-nickname";
        int age = 20;
        User savedUser = userService.save(nickname, age);

        /*
         * INSERT USER
         * */

        String newNickname = "new-nickname";
        User findUser = userService.changeNickname(savedUser.getId(), newNickname);

        /*
         * UPDATE USER
         * */

        /*
         * savedUser = {User@9086}
         *   id = 9
         *   nickname = "sample-nickname"
         *   age = 20
         *
         * findUser = {User@9146}
         *   id = 9
         *   nickname = "new-nickname"
         *   age = 20
         * */

        assertEquals(newNickname, findUser.getNickname());
        assertNotEquals(newNickname, savedUser.getNickname());
        assertEquals(findUser.getId(), savedUser.getId());
        assertNotEquals(findUser, savedUser);

        /*
         * No Rollback
         * */
    }

    @Test
    @DisplayName("Propagation: Required (기본 세팅 값. 현재 트랜잭션이 존재하면 이어서 받아들이고, 존재하지 않는다면 새로운 트랜잭션을 시작)")
    @Transactional
    void changeNicknameAsRequiredTransactionalTest() {
        String nickname = "sample-nickname";
        int age = 20;
        User savedUser = userService.save(nickname, age);

        /*
         * No INSERT USER
         * */

        String newNickname = "new-nickname";
        User findUser = userService.changeNickname(savedUser.getId(), newNickname);

        /*
         * No UPDATE USER
         * */

        /*
         * savedUser = {User@9055}
         *   id = 11
         *   nickname = "new-nickname"
         *   age = 20
         *
         * findUser = {User@9055}
         *   id = 11
         *   nickname = "new-nickname"
         *   age = 20
         * */

        assertEquals(findUser, savedUser);

        /*
         * Rollback Well
         * */
    }

    @Test
    @DisplayName("Propagation: Supports (현재 트랜잭션이 존재하면 이어서 받아들이고, 존재하지 않는다면 굳이 생성하지 않고 Non-트랜잭션 상태로 실행)")
    @Transactional(propagation = Propagation.SUPPORTS)
    void changeNicknameAsSupportsTransactionalTest() {
        String nickname = "sample-nickname";
        int age = 20;
        User savedUser = userService.save(nickname, age);

        /*
         * INSERT USER
         * */

        String newNickname = "new-nickname";
        User findUser = userService.changeNickname(savedUser.getId(), newNickname);

        /*
         * UPDATE USER
         * */

        /*
         * savedUser = {User@9086}
         *   id = 6
         *   nickname = "sample-nickname"
         *   age = 20
         *
         * findUser = {User@9151}
         *   id = 6
         *   nickname = "new-nickname"
         *   age = 20
         * */

        assertEquals(newNickname, findUser.getNickname());
        assertNotEquals(newNickname, savedUser.getNickname());
        assertEquals(findUser.getId(), savedUser.getId());
        assertNotEquals(findUser, savedUser);

        /*
         * No Rollback
         * */
    }

    /*
     * 현재 트랜잭션이 존재하면 이어서 받아들이고, 존재하지 않는다면 예외를 던진다.
     * */
    @Test
    @DisplayName("Propagation - Mandatory")
    @Transactional(propagation = Propagation.MANDATORY)
    void changeNicknameAsMandatoryTransactionalTest() {
        /*
         * IllegalTransactionStateException 발생
         * */
        System.out.println("실행 될까?");
    }

    @Test
    @DisplayName("Propagation: Requires New (현재 트랜잭션이 존재하면 중단하고, 새로운 트랜잭션을 시작)")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void changeNicknameAsRequiresNewTransactionalTest() {
        String nickname = "sample-nickname";
        int age = 20;
        User savedUser = userService.save(nickname, age);

        /*
         * No INSERT USER
         * */

        String newNickname = "new-nickname";
        User findUser = userService.changeNickname(savedUser.getId(), newNickname);

        /*
         * No UPDATE USER
         * */

        /*
         * savedUser = {User@9055}
         *   id = 8
         *   nickname = "new-nickname"
         *   age = 20
         *
         * findUser = {User@9055}
         *   id = 8
         *   nickname = "new-nickname"
         *   age 20
         * */

        assertEquals(findUser, savedUser);
        assertEquals(newNickname, savedUser.getNickname());

        /*
         * Rollback Well
         * */
    }
}