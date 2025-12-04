package com.tecsup.evaluacion03.aspect;

import com.tecsup.evaluacion03.model.Bitacora;
import com.tecsup.evaluacion03.service.BitacoraService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditoriaAspect {

    @Autowired
    private BitacoraService bitacoraService;

    /**
     * Registra operaciones de guardado (CREATE/UPDATE)
     * Excluye BitacoraServiceImpl para evitar recursión infinita
     */
    @AfterReturning(
            pointcut = "execution(* com.tecsup.evaluacion03.service.impl.*ServiceImpl.save(..)) && !execution(* com.tecsup.evaluacion03.service.impl.BitacoraServiceImpl.save(..))",
            returning = "result"
    )
    public void auditarGuardado(JoinPoint joinPoint, Object result) {
        try {
            String usuario = obtenerUsuarioActual();
            String entidad = extraerNombreEntidad(joinPoint);
            Long entidadId = extraerIdEntidad(result);

            Bitacora bitacora = new Bitacora(
                    usuario,
                    "CREAR",
                    entidad,
                    entidadId,
                    "Entidad creada/actualizada"
            );
            bitacoraService.save(bitacora);
        } catch (Exception e) {
            // Log the error but don't propagate to avoid breaking the main flow
            System.err.println("Error en auditoría: " + e.getMessage());
        }
    }

    /**
     * Registra operaciones de eliminación
     * Excluye BitacoraServiceImpl para evitar recursión infinita
     */
    @AfterReturning(
            pointcut = "execution(* com.tecsup.evaluacion03.service.impl.*ServiceImpl.delete(..)) && !execution(* com.tecsup.evaluacion03.service.impl.BitacoraServiceImpl.delete(..))"
    )
    public void auditarEliminacion(JoinPoint joinPoint) {
        try {
            String usuario = obtenerUsuarioActual();
            String entidad = extraerNombreEntidad(joinPoint);
            Object[] args = joinPoint.getArgs();
            Long entidadId = args.length > 0 ? (Long) args[0] : null;

            Bitacora bitacora = new Bitacora(
                    usuario,
                    "ELIMINAR",
                    entidad,
                    entidadId,
                    "Entidad eliminada"
            );
            bitacoraService.save(bitacora);
        } catch (Exception e) {
            System.err.println("Error en auditoría: " + e.getMessage());
        }
    }

    /**
     * Registra excepciones
     * Excluye BitacoraServiceImpl para evitar recursión infinita
     */
    @AfterThrowing(
            pointcut = "execution(* com.tecsup.evaluacion03.service.impl.*ServiceImpl.*(..)) && !execution(* com.tecsup.evaluacion03.service.impl.BitacoraServiceImpl.*(..))",
            throwing = "exception"
    )
    public void auditarExcepcion(JoinPoint joinPoint, Exception exception) {
        try {
            String usuario = obtenerUsuarioActual();
            String entidad = extraerNombreEntidad(joinPoint);

            Bitacora bitacora = new Bitacora(
                    usuario,
                    "EXCEPCION",
                    entidad,
                    null,
                    "Error: " + exception.getMessage()
            );
            bitacoraService.save(bitacora);
        } catch (Exception e) {
            System.err.println("Error en auditoría: " + e.getMessage());
        }
    }

    private String obtenerUsuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "SYSTEM";
    }

    private String extraerNombreEntidad(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        return className.substring(className.lastIndexOf('.') + 1).replace("ServiceImpl", "");
    }

    private Long extraerIdEntidad(Object entity) {
        try {
            return (Long) entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            return null;
        }
    }
}
