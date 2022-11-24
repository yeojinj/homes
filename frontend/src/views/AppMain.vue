<template>
  <div>
    <div id="main-image">
      <div class="container">
        <div class="title-text" style="text-align: left">
          <h2>
            <b>홈즈로<br />나에게 딱 맞게</b>
          </h2>
          <br />
          <br />
          <h5>
            <span class="title-highlight">실거래가</span>부터 <span class="title-highlight">주변 상권</span>까지 한 번에
          </h5>
        </div>
        <div class="title-image">
          <img id="homes-image" src="@/assets/homes_title.svg" />
        </div>
        <!-- <svg
          class="pulse"
          viewBox="0 0 1024 1024"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
          xmlns:xlink="http://www.w3.org/1999/xlink"
        >
          <circle id="Oval1" cx="512" cy="512" r="380"></circle>
        </svg> -->
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

.container {
  position: relative;
  z-index: 0;
  background-color: #fff7f8;
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 50vh;
  max-width: 100%;
  overflow: hidden;
}

@keyframes fadeInUp {
  0% {
    opacity: 0;
    transform: translate3d(0, -40%, 0);
  }
  30% {
    opacity: 0;
    transform: translate3d(0, -40%, 0);
  }
  to {
    opacity: 1;
    transform: translateZ(0, -50%);
  }
}

.title-text {
  animation: fadeInUp 1s;
  position: absolute;
  top: 50%;
  left: 25%;
  transform: translate(0, -50%);
}

.title-highlight {
  color: #3f667a;
  font-weight: bold;
}

.title-image {
  animation: fadeInUp 1s;
  position: absolute;
  top: 50%;
  left: 60%;
  transform: translate(0, -50%);
}

#homes-image {
  width: 300px;
}

/* h2 h5 {
  animation: fadeInUp 1s;
} */

/* .pulse {
  z-index: -1;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  max-width: 30rem;
} */
/* .pulse > circle {
  fill: #3f667a;
  transform: scale(0);
  opacity: 0;
  transform-origin: 50% 50%;
  animation: pulse 2s cubic-bezier(0.5, 0.5, 0, 1);
} */
/* @keyframes pulse {
  25% {
    opacity: 0.4;
  }

  100% {
    transform: scale(1);
  }
} */
</style>
