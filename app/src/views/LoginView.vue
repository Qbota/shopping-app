<template>
    <v-content>
        <v-container>
            <v-row justify="center" align="center">
                <LoginComponent/>
            </v-row>
        </v-container>
    </v-content>
</template>

<script>

    import LoginComponent from "@/components/LoginComponent";
    export default {
        name: "LoginView",
        components: {LoginComponent},
    }
</script>
