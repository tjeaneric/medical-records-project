const express = require("express");

const app = express();

const fetch = (...args) =>
  import("node-fetch").then(({ default: fetch }) => fetch(...args));

app.use(express.json());

app.get("/", async (req, res) => {
  const response = await fetch(
    "http://127.0.0.1:3000/api/v1/users/all.csv",
    {
      method: "GET",
      headers: { Authorization: req.headers.authorization },
    }
  );
  const data = await response.text();

  res.status(200).contentType("text/plain").send(data);
});

app.get("/all_data", async (req, res) => {
  const response = await fetch("http://127.0.0.1:3000/api/v1/get-data", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: req.headers.authorization,
    },
  });
  const data = await response.json();

  res.status(response.status).json({ all_data: data });
});

app.post("/", async (req, res) => {
  const response = await fetch(
    `http://127.0.0.1:3000/api/v1/users/create_user`,
    {
      method: "POST",
      body: JSON.stringify(req.body),
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
  const data = await response.json();
  res.status(response.status).json({ User: data });
});

app.post("/login", async (req, res) => {
  const response = await fetch(
    `http://127.0.0.1:3000/api/v1/users/login`,
    {
      method: "POST",
      body: JSON.stringify(req.body),
      headers: { "Content-Type": "application/json" },
    }
  );
  const data = await response.json();
  res.status(200).json({ User: data });
});

module.exports = app;
