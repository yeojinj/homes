<template>
  <div>
    <div id="main-image">
      <div class="container">
        <h1>HOMES</h1>
        <svg
          class="pulse"
          viewBox="0 0 1024 1024"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
          xmlns:xlink="http://www.w3.org/1999/xlink"
        >
          <circle id="Oval1" cx="512" cy="512" r="512"></circle>
          <circle id="Oval2" cx="512" cy="512" r="512"></circle>
          <circle id="Oval3" cx="512" cy="512" r="512"></circle>
        </svg>
      </div>
    </div>

    <div id="main-boards">
      <main-news></main-news>
      <main-notice></main-notice>
    </div>
  </div>
</template>

<script>
import MainNews from "@/components/main/MainNews.vue";
import MainNotice from "@/components/main/MainNotice.vue";

export default {
  name: "AppMain",
  props: {
    msg: String,
  },
  components: {
    MainNews,
    MainNotice,
  },
};
</script>

<style scoped>
#main-image {
  margin-top: 80px;
}

#main-boards {
  margin-top: 80px;
  margin-bottom: 80px;
  display: inline-block;
}

@font-face {
  font-family: "Cafe24ClassicType-Regular";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2210-2@1.0/Cafe24ClassicType-Regular.woff2")
    format("woff2");
  font-weight: normal;
  font-style: normal;
}

h1 {
  font-family: "Cafe24ClassicType-Regular";
  font-size: 5rem;
  text-shadow: 0px 1px 0px rgba(255, 255, 255, 1);
  color: #343434;
}

.container {
  position: relative;
  z-index: 0;
  background-color: #ededed;
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 50vh;
  max-width: 100%;
  overflow: hidden;
}

.pulse {
  z-index: -1;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-width: 30rem;
}

.pulse > circle {
  fill: #7a4c51;
  transform: scale(0);
  opacity: 0;
  transform-origin: 50% 50%;
  animation: pulse 2s cubic-bezier(0.5, 0.5, 0, 1);
}

#Oval2 {
  fill: #3f667a;
  animation: pulse 2s 0.75s cubic-bezier(0.5, 0.5, 0, 1);
}

#Oval3 {
  fill: #c7c35d;
  animation: pulse 2s 1.5s cubic-bezier(0.5, 0.5, 0, 1);
}

@keyframes pulse {
  25% {
    opacity: 0.4;
  }

  100% {
    transform: scale(1);
  }
}
</style>
