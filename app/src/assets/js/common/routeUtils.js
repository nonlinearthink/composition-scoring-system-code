import route from "../../../router/index";

function onRouteBack(callback) {
  if (typeof callback == "function") callback();
  route.go(-1);
}

function onRouteChange(to, callback) {
  if (typeof callback == "function") callback();
  route.push(to);
}

export default { onRouteBack, onRouteChange };
