"use client";
import { useState } from "react";
import Tippy from "@tippyjs/react";
import "tippy.js/dist/tippy.css";
import { motion } from "framer-motion";

export default function RegisterPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [darkMode, setDarkMode] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

 /* const handleLogin = async () => {
    setErrorMessage("");
    setSuccessMessage("");

    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });

      if (response.ok) {
        const result = await response.text(); // Assuming the backend returns a string (e.g., "success")
        if (result === "success") {
          setSuccessMessage("Login successful! Redirecting...");
        } else {
          setErrorMessage("Invalid email or password.");
        }
      } else {
        setErrorMessage("Invalid email or password.");
      }
    } catch (error) {
      setErrorMessage("An error occurred. Please try again later.");
      console.error("Login error:", error);
    }
  };*/

  const handleLogin = async()=>{
    setErrorMessage("");
    setSuccessMessage("");
    try{
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({email,password}),});
        if (response.ok){
          const token = await response.text();
          localStorage.setItem("authToken",token);
          setSuccessMessage("Login successful!")
         /* setTimeout(() => {
            window.location.href = "/dashboard"; // Redirect to a protected route (e.g., dashboard)
          }, 2000);*/

        }else{
          const errorResponse = await response.text();
          setErrorMessage(errorResponse || "Invalid email or password.");
        }


    } catch (error) { 
      setErrorMessage("An error occurred. Please try again later.");
      console.error("Login error:", error);
      }
   };

  return (
    <div className="flex min-h-screen">
      {/* Left Section */}
      <div className="w-1/2 p-10">
        <h1 className="text-4xl font-bold text-blue-700">Digital</h1>

        <br />
        <br />
        <br />

        <div className="mb-6">
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5 }}
          >
            <h3 className="text-3xl font-semibold text-blue-700">
              Artificial Intelligence Driving Results
            </h3>
            <h3 className="text-3xl font-semibold text-blue-700">
              Results For The Travel Industry
            </h3>
          </motion.div>
        </div>

        <h4 className="text-1g text-gray-600 mb-4">
          Welcome back! Please login to your account
        </h4>

        {/* Email Section */}
        <div className="mb-4">
          <h4 className="text-sm font-semibold text-gray-700 mb-2">Email</h4>
          <input
            value={email}
            type="email"
            onChange={(e) => setEmail(e.target.value)}
            className="w-full border border-gray-300 rounded-md p-2"
            placeholder="Enter your email"
          />
        </div>

        {/* Password Section */}
        <div className="mb-4">
          <h4 className="text-sm font-semibold text-gray-700 mb-2">Password</h4>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="border w-full border-gray-300 rounded-md p-2"
            placeholder="Enter your password"
          />
        </div>

        {/* Remember Me and Forgot Password */}
        <div className="flex justify-between items-center mb-6">
          <label className="flex items-center text-gray-600">
            <input type="checkbox" className="mr-2" />
            Remember Me
          </label>
          <a href="#" className="text-blue-500 text-sm hover:underline">
            Forget Password?
          </a>
        </div>

        {/* Login Error/Success Messages */}
        {errorMessage && (
          <p className="text-red-500 text-sm mb-4">{errorMessage}</p>
        )}
        {successMessage && (
          <p className="text-green-500 text-sm mb-4">{successMessage}</p>
        )}

        {/* Buttons */}
        <div className="flex justify-between items-center mb-6 gap-8">
          <button
            className="text-white bg-blue-500 hover:bg-blue-700 rounded-md p-2 w-full"
            onClick={handleLogin}
          >
            Login
          </button>
          <button className="text-blue-500 hover:text-blue-700 rounded-md p-2 border border-blue-500 w-full">
            Sign Up
          </button>
        </div>

        {/* Social Media Login */}
        <div className="flex justify-between items-center mb-6">
          <p className="text-center text-gray-600">Or login with</p>
          <div className="flex justify-center gap-4">
            <a href="#" className="text-blue-500 hover:underline">
              Facebook
            </a>
            <a href="#" className="text-blue-500 hover:underline">
              LinkedIn
            </a>
            <a href="#" className="text-blue-500 hover:underline">
              Google
            </a>
          </div>
        </div>
      </div>

      {/* Right Section */}
      <div className="w-1/2 bg-gray-100">
        <div className="w-full flex justify-between align-center p-6 mb-20">
          <ul className="flex space-x-8 text-gray-600 justify-between">
            <li className="hover:text-blue-500 cursor-pointer hover:underline">
              Home
            </li>
            <li className="hover:text-blue-500 cursor-pointer hover:underline">
              About Us
            </li>
            <li className="hover:text-blue-500 cursor-pointer hover:underline">
              Blog
            </li>
            <li className="hover:text-blue-500 cursor-pointer hover:underline">
              Pricing
            </li>
          </ul>
          <button
            onClick={() => {
              setDarkMode(!darkMode);
            }}
            className={`border border-5 rounded-md ${
              darkMode
                ? "bg-gray-800 text-white"
                : "bg-white text-black"
            }`}
          >
            Dark Mode
          </button>
        </div>

        <div className="flex-1 flex items-center justify-center">
          <img
            src="https://cdn.mos.cms.futurecdn.net/VFLt5vHV7aCoLrLGjP9Qwm-1200-80.jpg"
            alt="Person on bike illustration"
            className="w-2/3 h-auto"
          />
        </div>
      </div>
    </div>
  );
}
