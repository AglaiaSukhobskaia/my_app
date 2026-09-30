package com.aglaya.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtService {
    @Value("${jwt.secret}")
    String secretKey;

    @Value("${jwt.expiration-hours}")
    long expirationHours;

    /**
     * Генерация JWT-токена для аутентифицированного пользователя
     *
     * @param userDetails данные пользователя, для которого создаётся токен
     * @return подписанный JWT-токен в строковом представлении
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Создание JWT-токена
     *
     * @param claims  дополнительные данные для токена
     * @param subject идентификатор пользователя (username)
     * @return подписанный JWT-токен
     */
    private String createToken(Map<String, Object> claims, String subject) {
        var now = Instant.now();

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expirationHours, ChronoUnit.HOURS)))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Создание криптографического ключа для подписи и проверки JWT-токенов
     * (используется алгоритм HMAC-SHA256)
     *
     * @return секретный ключ для подписи
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Извлечение username (subject) из JWT-токена
     *
     * @param token JWT-токен в строковом представлении
     * @return username пользователя, записанный в токене
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Извлечение claim из JWT-токена
     *
     * @param token          JWT-токен в строковом представлении
     * @param claimsResolver функция, определяющая, какое поле извлечь
     * @param <T>            тип извлекаемого значения
     * @return значение указанного поля из токена
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        var claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Парсинг JWT-токена и извлечение всех claims
     *
     * @param token JWT-токен в строковом представлении
     * @return объект Claims, содержащий все данные из токена
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Проверка валидности JWT-токена для конкретного пользователя
     *
     * @param token       JWT-токен в строковом виде
     * @param userDetails данные пользователя, с которыми сверяется токен
     * @return true, если токен валиден; false — если нет
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        var username = extractUsername(token);
        return Objects.equals(username, userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Провка, истёк ли срок действия JWT-токена
     *
     * @param token JWT-токен в строковом представлении
     * @return true, если токен просрочен; false — если ещё действителен
     */
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(Date.from(Instant.now()));
    }
}
