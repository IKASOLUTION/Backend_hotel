package com.hotel.bf.dto.searchSpecification;


import com.hotel.bf.domain.Sejours;
import com.hotel.bf.domain.User;
import com.hotel.bf.domain.enums.StatutSejour;
import com.hotel.bf.domain.enums.TypeDocument;
import com.hotel.bf.dto.SejoursDto;
import com.hotel.bf.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SejoursSpecification {
    private final UserService userService;

    public static Specification<Sejours> hasNomClient(String nomClient) {
        return (root, query, cb) ->
                nomClient == null ? null : cb.like(cb.lower(root.get("nomClient")), "%" + nomClient.toLowerCase() + "%");
    }

    public static Specification<Sejours> hasPrenomClient(String prenomClient) {
        return (root, query, cb) ->
                prenomClient == null ? null : cb.like(cb.lower(root.get("prenomClient")), "%" + prenomClient.toLowerCase() + "%");
    }

    public static Specification<Sejours> hasDateNaissanceBetween(LocalDate dateDebut, LocalDate dateFin) {
        return (root, query, cb) -> {
            if (dateDebut == null && dateFin == null) return null;
            if (dateDebut != null && dateFin != null) {
                return cb.between(root.get("dateDaissance"), dateDebut, dateFin);
            }
            if (dateDebut != null) {
                return cb.greaterThanOrEqualTo(root.get("dateDaissance"), dateDebut);
            }
            return cb.lessThanOrEqualTo(root.get("dateDaissance"), dateFin);
        };
    }

    public static Specification<Sejours> hasLieuNaissance(String lieuNaissance) {
        return (root, query, cb) ->
                lieuNaissance == null ? null : cb.like(cb.lower(root.get("lieuDaissance")), "%" + lieuNaissance.toLowerCase() + "%");
    }

    public static Specification<Sejours> hasTypeDocument(TypeDocument typeDocument) {
        return (root, query, cb) ->
                typeDocument == null ? null : cb.equal(root.get("typeDocument"), typeDocument);
    }

    public static Specification<Sejours> hasNumeroDocument(String numeroDocument) {
        return (root, query, cb) ->
                numeroDocument == null ? null : cb.like(cb.lower(root.get("numeroDocument")), "%" + numeroDocument.toLowerCase() + "%");
    }

    public static Specification<Sejours> hasLieuResidence(String lieuResidence) {
        return (root, query, cb) ->
                lieuResidence == null ? null : cb.like(cb.lower(root.get("lieuResidence")), "%" + lieuResidence.toLowerCase() + "%");
    }

    public static Specification<Sejours> hasNumeroChambre(String numeroChambre) {
        return (root, query, cb) ->
                numeroChambre == null ? null : cb.equal(root.get("numeroChambre"), numeroChambre);
    }

    public static Specification<Sejours> hasTelephone(String telephone) {
        return (root, query, cb) ->
                telephone == null ? null : cb.like(root.get("telephone"), "%" + telephone + "%");
    }

    public static Specification<Sejours> hasDateEntreeBetween(LocalDateTime dateDebut, LocalDateTime dateFin) {
        return (root, query, cb) -> {
            if (dateDebut == null && dateFin == null) return null;
            if (dateDebut != null && dateFin != null) {
                return cb.between(root.get("dateEntree"), dateDebut, dateFin);
            }
            if (dateDebut != null) {
                return cb.greaterThanOrEqualTo(root.get("dateEntree"), dateDebut);
            }
            return cb.lessThanOrEqualTo(root.get("dateEntree"), dateFin);
        };
    }

    public static Specification<Sejours> hasDateSortieBetween(LocalDateTime dateDebut, LocalDateTime dateFin) {
        return (root, query, cb) -> {
            if (dateDebut == null && dateFin == null) return null;
            if (dateDebut != null && dateFin != null) {
                return cb.between(root.get("dateSortie"), dateDebut, dateFin);
            }
            if (dateDebut != null) {
                return cb.greaterThanOrEqualTo(root.get("dateSortie"), dateDebut);
            }
            return cb.lessThanOrEqualTo(root.get("dateSortie"), dateFin);
        };
    }

    public static Specification<Sejours> hasStatut(StatutSejour statut) {
        return (root, query, cb) ->
                statut == null ? null : cb.equal(root.get("statut"), statut);
    }

    public static Specification<Sejours> hasNationalite(Long nationaliteId) {
        return (root, query, cb) ->
                nationaliteId == null ? null : cb.equal(root.get("nationalite").get("id"), nationaliteId);
    }

    public static Specification<Sejours> hasAgentEntree(Long agentId) {
        return (root, query, cb) ->
                agentId == null ? null : cb.equal(root.get("agentEntree").get("id"), agentId);
    }

    public static Specification<Sejours> hasAgentSortie(Long agentId) {
        return (root, query, cb) ->
                agentId == null ? null : cb.equal(root.get("agentSortie").get("id"), agentId);
    }

    public Specification<Sejours> hasHotel(Long hotelId) {

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

    public Specification<Sejours> hasRegion(Long regionId) {
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

    public Specification<Sejours> hasProvince(Long provinceId) {
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

    public Specification<Sejours> hasCommune(Long communeId) {
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
    public static Specification<Sejours> hasDeleted(Boolean deleted) {
        return (root, query, cb) ->
                cb.equal(root.get("deleted"), deleted);
    }

    // Méthode pour combiner tous les critères
    public Specification<Sejours> buildSpecification(SejoursDto criteria) {
        return Specification
                .where(hasNomClient(criteria.getNomClient()))
                .and(hasPrenomClient(criteria.getPrenomClient()))
                .and(hasDeleted(Boolean.FALSE))
                .and(hasDateNaissanceBetween(criteria.getDateDaissance(), criteria.getDateDaissance()))
                .and(hasLieuNaissance(criteria.getLieuDaissance()))
                .and(hasTypeDocument(criteria.getTypeDocument()))
                .and(hasNumeroDocument(criteria.getNumeroDocument()))
                .and(hasLieuResidence(criteria.getLieuResidence()))
                .and(hasNumeroChambre(criteria.getNumeroChambre()))
                .and(hasTelephone(criteria.getTelephone()))
                .and(hasDateEntreeBetween(criteria.getDateEntree(), criteria.getDateEntree()))
                .and(hasDateSortieBetween(criteria.getDateSortie(), criteria.getDateSortie()))
                .and(hasStatut(criteria.getStatut()))
                .and(hasNationalite(criteria.getNationalite() != null ? criteria.getNationalite().getId() : null))
                .and(hasAgentEntree(criteria.getAgentEntree() != null ? criteria.getAgentEntree().getId() : null))
                .and(hasAgentSortie(criteria.getAgentSortie() != null ? criteria.getAgentSortie().getId() : null))
                .and(hasHotel(criteria.getHotel() != null ? criteria.getHotel().getId() : null))
                .and(hasRegion(criteria.getRegion() != null ? criteria.getRegion().getId() : null))
                .and(hasProvince(criteria.getProvince() != null ? criteria.getProvince().getId() : null))
                .and(hasCommune(criteria.getCommune() != null ? criteria.getCommune().getId() : null));
    }
}
