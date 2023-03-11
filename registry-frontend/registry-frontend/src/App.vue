<template>
    <Nav/>
    <Main/>
</template>

<script>
    import Main from "./components/Main.vue";
    import Nav from "./components/Nav.vue";

    export default {
      name: 'App',
      components: {
        Main,
        Nav
      }
    }
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>
