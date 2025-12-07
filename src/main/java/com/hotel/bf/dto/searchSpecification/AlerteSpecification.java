package com.hotel.bf.dto.searchSpecification;


import com.hotel.bf.domain.Alerte;
import com.hotel.bf.domain.User;
import com.hotel.bf.domain.enums.StatutAlerte;
import com.hotel.bf.domain.enums.TypeCorrespondance;
import com.hotel.bf.dto.AlerteDto;
import com.hotel.bf.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlerteSpecification {
    private final UserService userService;

    public static Specification<Alerte> hasNiveauCorrespondance(Double niveauCorrespondance) {
        return (root, query, cb) ->
                niveauCorrespondance == null ? null : cb.like(cb.lower(root.get("niveauCorrespondance")), "%" + niveauCorrespondance + "%");
    }

    public static Specification<Alerte> hasTypeCorrespondance(TypeCorrespondance typeCorrespondance) {
        return (root, query, cb) ->
                typeCorrespondance == null ? null : cb.equal(root.get("typeCorrespondance"), typeCorrespondance);
    }

    public static Specification<Alerte> hasStatutAlerte(StatutAlerte statutAlerte) {
        return (root, query, cb) ->
                statutAlerte == null ? null : cb.equal(root.get("statutAlerte"), statutAlerte);
    }

    public static Specification<Alerte> hasActionPrise(String actionPrise) {
        return (root, query, cb) ->
                actionPrise == null ? null : cb.like(cb.lower(root.get("actionPrise")), "%" + actionPrise.toLowerCase() + "%");
    }


    public static Specification<Alerte> hasDateAlerteBetween(LocalDateTime dateDebut, LocalDateTime dateFin) {
        return (root, query, cb) -> {
            if (dateDebut == null && dateFin == null) return null;
            if (dateDebut != null && dateFin != null) {
                return cb.between(root.get("dateAlerte"), dateDebut, dateFin);
            }
            if (dateDebut != null) {
                return cb.greaterThanOrEqualTo(root.get("dateAlerte"), dateDebut);
            }
            return cb.lessThanOrEqualTo(root.get("dateAlerte"), dateFin);
        };
    }

    public static Specification<Alerte> hasDateTraitementBetween(LocalDateTime dateDebut, LocalDateTime dateFin) {
        return (root, query, cb) -> {
            if (dateDebut == null && dateFin == null) return null;
            if (dateDebut != null && dateFin != null) {
                return cb.between(root.get("dateTraitement"), dateDebut, dateFin);
            }
            if (dateDebut != null) {
                return cb.greaterThanOrEqualTo(root.get("dateTraitement"), dateDebut);
            }
            return cb.lessThanOrEqualTo(root.get("dateTraitement"), dateFin);
        };
    }

    public static Specification<Alerte> hasListeNoire(Long listeNoireId) {
        return (root, query, cb) ->
                listeNoireId == null ? null : cb.equal(root.get("listeNoire").get("id"), listeNoireId);
    }

    public static Specification<Alerte> hasSejours(Long sejoursId) {
        return (root, query, cb) ->
                sejoursId == null ? null : cb.equal(root.get("sejours").get("id"), sejoursId);
    }

    public static Specification<Alerte> hasUserNotifie(Long userNotifieId) {
        return (root, query, cb) ->
                userNotifieId == null ? null : cb.equal(root.get("userNotifie").get("id"), userNotifieId);
    }

    public static Specification<Alerte> hasTraitePar(Long traiteParId) {
        return (root, query, cb) ->
                traiteParId == null ? null : cb.equal(root.get("traitePar").get("id"), traiteParId);
    }

    public Specification<Alerte> hasHotel(Long hotelId) {
        Long hotelCommuneId = hotelId;
        if (hotelCommuneId == null) {
            Optional<User> user = userService.getUserWithAuthorities();
            if (user.isPresent() && user.get().getHotel() != null) {
                hotelCommuneId = user.get().getHotel().getId();
            }
        }
        Long hotelIdToUse = hotelCommuneId;
        return (root, query, cb) ->
                hotelIdToUse == null ? null : cb.equal(root.get("hotel").get("id"), hotelIdToUse);
    }

    public Specification<Alerte> hasRegion(Long regionId) {
        Long finalRegionId = regionId;
        if (finalRegionId == null) {
            Optional<User> user = userService.getUserWithAuthorities();
            if (user.isPresent() && user.get().getRegion() != null) {
                finalRegionId = user.get().getRegion().getId();
            }
        }
        Long regionIdToUse = finalRegionId;
        return (root, query, cb) ->
                regionIdToUse == null ? null : cb.equal(root.get("region").get("id"), regionIdToUse);
    }

    public Specification<Alerte> hasProvince(Long provinceId) {
        Long finalProvinceId = provinceId;
        if (finalProvinceId == null) {
            Optional<User> user = userService.getUserWithAuthorities();
            if (user.isPresent() && user.get().getProvince() != null) {
                finalProvinceId = user.get().getProvince().getId();
            }
        }
        Long provinceIdToUse = finalProvinceId;
        return (root, query, cb) ->
                provinceIdToUse == null ? null : cb.equal(root.get("province").get("id"), provinceIdToUse);
    }

    public Specification<Alerte> hasCommune(Long communeId) {
        Long finalCommuneId = communeId;
        if (finalCommuneId == null) {
            Optional<User> user = userService.getUserWithAuthorities();
            if (user.isPresent() && user.get().getCommune() != null) {
                finalCommuneId = user.get().getCommune().getId();
            }
        }
        Long communeIdToUse = finalCommuneId;
        return (root, query, cb) ->
                communeIdToUse == null ? null : cb.equal(root.get("commune").get("id"), communeIdToUse);
    }
    public static Specification<Alerte> hasDeleted(Boolean deleted) {
        return (root, query, cb) ->
                cb.equal(root.get("deleted"), deleted);
    }

    // Méthode pour combiner tous les critères
    public Specification<Alerte> buildSpecification(AlerteDto criteria) {
        return Specification
                .where(hasNiveauCorrespondance(criteria.getNiveauCorrespondance()))
                .and(hasTypeCorrespondance(criteria.getTypeCorrespondance()))
                .and(hasStatutAlerte(criteria.getStatutAlerte()))
                .and(hasDeleted(Boolean.FALSE))
                .and(hasDateAlerteBetween(criteria.getDateDebutAlerte(), criteria.getDateFinAlerte()))
                .and(hasDateTraitementBetween(criteria.getDateDebutTraitement(), criteria.getDateFinTraitement()))
                .and(hasActionPrise(criteria.getActionPrise()))
                .and(hasListeNoire(criteria.getListeNoire() != null ? criteria.getListeNoire().getId() : null))
                .and(hasSejours(criteria.getSejours() != null ? criteria.getSejours().getId() : null))
                .and(hasUserNotifie(criteria.getUserNotifie() != null ? criteria.getUserNotifie().getId() : null))
                .and(hasTraitePar(criteria.getTraitePar() != null ? criteria.getTraitePar().getId() : null))
                .and(hasHotel(criteria.getHotel() != null ? criteria.getHotel().getId() : null))
                .and(hasRegion(criteria.getRegion() != null ? criteria.getRegion().getId() : null))
                .and(hasProvince(criteria.getProvince() != null ? criteria.getProvince().getId() : null))
                .and(hasCommune(criteria.getCommune() != null ? criteria.getCommune().getId() : null));
    }
}
