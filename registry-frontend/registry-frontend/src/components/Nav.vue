<template>
    <div class="nav">
        <div class="pure-menu pure-menu-horizontal">
            <a href="/" class="pure-menu-heading pure-menu-link">
                <img class="nav_img" src="../assets/About_Monsters_Logo.png">
            </a>
            <ul class="pure-menu-list">
                <li class="pure-menu-item">
                    <a href="/historypage" class="pure-menu-link">History</a>
                </li>
                <li class="pure-menu-item">
                    <a href="/sellpage" class="pure-menu-link">Sell Stuff</a>
                </li>
            </ul>
        </div>
    </div>

</template>

<script>
    export default {
        name: "Nav"
    }
</script>

<style scoped>
    @import "https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css";

    .nav_img {
        width: 125px;
        height: 125px;
    }
</style>