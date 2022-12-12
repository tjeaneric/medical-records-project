const app = require("./app");

const port = 4000;

const server = app.listen(port, () =>
  console.log(`App listening on ${port}!....`)
);
