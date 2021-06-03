import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';
import '@fortawesome/fontawesome-free/css/all.css'

Vue.use(Vuetify);

export default new Vuetify({
    icons: {
        iconfont: 'fa'
    },
    theme: {
        themes: {
            light:{
                primary: '#faf3f3',
                secondary: '#e1e5ea',
                accent: '#a7bbc7',
                error: '#FF5252',
                info: '#2196F3',
                success: '#4CAF50',
                warning: '#FFC107',
                background: "#c8c2bc"
            }
        }
    }
});
