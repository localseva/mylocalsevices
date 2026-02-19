//package com.mylocalservices.app.filters;
//
//import com.mylocalservices.app.entity.User;
//import com.mylocalservices.app.repository.UserRepository;
//import com.mylocalservices.app.service.JwtService;
//import io.jsonwebtoken.Claims;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//    private final UserRepository userRepository;
//
//    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
//        this.jwtService = jwtService;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        final String header = request.getHeader("Authorization");
//        if (header == null || !header.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        final String token = header.substring(7);
//        if (!jwtService.validateToken(token)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        Claims claims = jwtService.parseClaims(token);
//        String phone = claims.getSubject();
//        Optional<User> userOpt = userRepository.findByPhone(phone);
//
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//            String role = claims.get("role", String.class);
//            var auth = new UsernamePasswordAuthenticationToken(
//                    user,
//                    null,
//                    List.of(new SimpleGrantedAuthority("ROLE_" + role))
//            );
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
