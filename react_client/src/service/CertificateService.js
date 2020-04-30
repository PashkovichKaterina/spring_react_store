import Util from "./Util";
import AuthorizationLogic from "./AuthorizationLogic";
import RedirectLogic from "./RedirectLogic";

const CERTIFICATES_URL = "/certificates";
const CERTIFICATE_BY_ID_URL = "/certificates/{0}";
const USER_ORDERS = "/users/{0}/orders";
const DELETE_METHOD = "DELETE";
const POST_METHOD = "POST";
const PUT_METHOD = "PUT";

class CertificateService {
    delete(certificateId) {
        return fetch(Util.format(CERTIFICATE_BY_ID_URL, certificateId), {
            method: DELETE_METHOD,
            headers: new Headers({
                'Authorization': 'Bearer ' + AuthorizationLogic.getToken()
            })
        });
    }

    add(certificate) {
        return fetch(CERTIFICATES_URL, {
            method: POST_METHOD,
            headers: new Headers({
                'Authorization': 'Bearer ' + AuthorizationLogic.getToken(),
                'Content-Type': 'application/json'
            }),
            body: JSON.stringify(certificate)
        });
    }

    edit(certificateId, certificate) {
        return fetch(Util.format(CERTIFICATE_BY_ID_URL, certificateId), {
            method: PUT_METHOD,
            headers: new Headers({
                'Authorization': 'Bearer ' + AuthorizationLogic.getToken(),
                'Content-Type': 'application/json'
            }),
            body: JSON.stringify(certificate)
        });
    }

    buy(certificateId) {
        const order = {
            certificates: [{id: Number(certificateId)}]
        };
        return fetch(Util.format(USER_ORDERS, AuthorizationLogic.getUserId(), certificateId), {
            method: POST_METHOD,
            headers: new Headers({
                'Authorization': 'Bearer ' + AuthorizationLogic.getToken(),
                'Content-Type': 'application/json'
            }),
            body: JSON.stringify(order)
        });
    }

    getCertificate(certificateId) {
        return fetch(Util.format(CERTIFICATE_BY_ID_URL, certificateId));
    }

    getAllCertificate(search, tags, price, minPrice, maxPrice, page, perPage) {
        return fetch(CERTIFICATES_URL + RedirectLogic.formUrl(search, tags, price, minPrice, maxPrice, page, perPage) + "&sort=creation_date");
    }

    getCertificateByUser(search, tags, price, minPrice, maxPrice, page, perPage) {
        return fetch("/users/" + AuthorizationLogic.getUserId() + CERTIFICATES_URL + RedirectLogic.formUrl(search, tags, price, minPrice, maxPrice, page, perPage), {
                headers: new Headers({
                    'Authorization': 'Bearer ' + AuthorizationLogic.getToken(),
                    'Content-Type': 'application/json'
                })
            }
        )
    }
}

export default new CertificateService();