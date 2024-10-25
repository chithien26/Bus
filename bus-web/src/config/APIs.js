import axios from "axios"
import cookie from "react-cookies"

const BASE_URL = 'http://localhost:8080'

export const endpoints = {
    'route': '/route',
    'station': '/station',
    'login': '/login',
    'bustrip': '/bustrip',
    'schedule': '/schedule',
    'favourite': '/favourite',
}

export const authAPIs = () => {
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            'Authorization': cookie.load("access-token")
        }
    })
}

export default axios.create({
    baseURL: BASE_URL
});