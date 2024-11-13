import { createApp } from 'vue'
import Router from './router'
import Store from './store'
import App from './App.vue'
import { permission } from "./directives/permission";
import LayJsonSchemaForm from "@layui/json-schema-form";
// import './mockjs'

const app = createApp(App)

app.use(Store);
app.use(Router);
app.use(LayJsonSchemaForm);

app.directive("permission",permission);
app.mount('#app');
