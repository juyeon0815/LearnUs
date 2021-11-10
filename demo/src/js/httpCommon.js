
import axios from "axios";

export default {
    axios: axios.create({
        baseURL: "http://localhost:8080",
        Headers: {
            "Content-Type":"application/json",
        }
    })
}