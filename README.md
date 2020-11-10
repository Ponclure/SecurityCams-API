# Security Cameras API [1.15 - 1.16] [Friendly Camera API for Developers]

## Introduction

Security Cameras API is one of the **best** Security Camera API you will get, featuring many options for developers who like coding around security cameras. Features include a well formatted readable code friendly for developers to work around, an active Github which has commits often being pushed, and a discord for support if you require assistance. The code is open source and can be located [here](https://github.com/Ponclure/SecurityCams-API), which contains both the API and the example plugin we have provided to point users to the right direction. To use the API, download the jar provided in the Spigot resource and shade it inside your plugin. 

What do Security Cameras look like: Security Cameras API relies on the idea of using Armorstands which are invisible and have a "Camera" as its head.

Security Cameras API also uses another one of our libraries, `Simple NPC Framework` which can be located [here](https://github.com/Ponclure/Simple-NPC-Framework). You may ask, *why do we must include npc's in this library?* Well the answer is that when a player uses a Camera, a fake NPC with the same exact look as the player is placed at the same location the player was originally in. The actual player is tped to the camera and put into spectator mode, so it looks like on their screen they are viewing the camera, but on other player's screens they just see the NPC. We have originally used Citizens, but because the library is very heavy and bulky for an API like this, we have proceeded to create our own NPC library which would be used for our plugins. You do **not** have to add this other API into your workspace, it has already been shaded into the Security Camera API.

## API Usage

The class which is needed for the API to be useful is the `CameraManager` class, which can be defined using a constructor that accepts a `Plugin` which you pass in (for your own plugin) and a `File` which is needed to store the necessary configurations the camera requires. 

Once you are done defining your `CameraManager` class, adding a camera is as convienant as `CameraManager#addCamera(Location loc, String name)` and removing a camera is as simple as `CameraManager#removeCamera(Location loc, String name)`. 

When a camera is added or deleted, the `YAML` file storing the camera configurations is updated. File modifying is modified asynchronously, and uses a lock to make sure that the same file isn't being written by multiple threads, causing an `Exception` to occur. 

Camera load is also loaded asynchronously, and will be cached on Camera Manager creation. This also as well uses a lock to make sure that it isn't being read by multiple threads.

To *use* a camera, you would use the `CameraManager#addWatcher(Player player, Camera camera)` to add a watcher to a camera. Cameras by default support multiple watchers at once. In order to  remove a watcher from a Camera, use the `CameraManager#removeWatcher(Player player, boolean forceRemove)` method which will either force remove or normally remove a player from a camera view. By default, sneaking will let the player out of the Camera, and teleport the player to it's original location. 

In addition from methods, there are also events as well such as the `CameraSetEvent`, `CameraDestroyEvent`, `CameraEnterEvent`, and `CameraExitEvent`.

## Conclusion
And that's pretty much it. We will be adding more features as we go on, but check out our organization's website located at [https://ponclure.github.io](https://ponclure.github.io), which contains all of our projects and source code made by collaborative developers. I hope this API is useful!
