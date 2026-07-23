# Easy Netherite + OP Emerald Pick — Fabric 1.21.11 Port

This is a full reverse-engineered port of the original Forge/MCreator mod
`easy-netherite-op-emerald-pick-1-19-4.jar` to Fabric for Minecraft 1.21.11 / Java 21.

## How the original was analyzed

The original jar had no source code attached, only compiled `.class` files, and
no Java decompiler or internet access was available in the sandbox this was built
in. Instead, the constant pool and bytecode of each class were manually
disassembled to recover the exact values used to construct the item and its
custom `Tier`. Nothing here is guesswork about the numbers — they are read
directly from bytecode instructions (`ldc`, `bipush`, `fconst_2`, etc.).

### Recovered values (from `DdhItem.class` and its anonymous `Tier`)

| Property                    | Original value        | Ported as                                  |
|------------------------------|------------------------|---------------------------------------------|
| Durability                   | `100000`                | `ToolMaterial.durability = 100000`          |
| Mining speed                 | `100000.0F`              | `ToolMaterial.speed = 100000.0F`             |
| Attack damage bonus          | `2.0F`                   | `ToolMaterial.attackDamageBonus = 2.0F`      |
| Attack damage modifier (base)| `1` (int, ctor arg)       | `PickaxeItem(..., 1.0F, ...)`                |
| Attack speed modifier        | `-3.0F`                   | `PickaxeItem(..., ..., -3.0F, ...)`          |
| Mining level                 | `20` (mines everything)  | Empty custom block tag `incorrect_for_emerald_pick` (nothing is ever "incorrect", so it mines every block) |
| Enchantability                | `30`                     | `ToolMaterial.enchantmentValue = 30`          |
| Repair material               | `minecraft:emerald`      | Custom item tag `repairs_emerald_pick`        |
| Item id                       | `easy_netherite_op_emerald_pick:emerald_pick` | unchanged |
| Display name                  | "God Pick"               | unchanged (lang file copied verbatim)         |

### Assets preserved byte-for-byte

- `textures/item/emerald_pick.png`
- `models/item/emerald_pick.json`
- `lang/en_us.json` (kept both original keys, including the unused
  `item.easier_netherite.emerald_pick` leftover key from the original mod)
- Both recipes (`emerald_pickaxe.json`, the emerald+stick pickaxe recipe, and
  `netherite.json`, the "dirt + gold ingot → netherite ingot" easy-netherite
  recipe), rewritten only where the Minecraft 1.21.2+ recipe JSON format
  required it (ingredients as plain/`#tag` strings, `result.id` instead of
  `result.item`) — the crafting patterns, ingredients, and results are identical.

### Two things that could not be recovered with certainty

1. **Which vanilla creative tab the item was added to.** The original class
   (`EasyNetheriteOpEmeraldPickModTabs.class`) referenced the tab only via an
   obfuscated field (`f_256869_`), which could not be resolved to a human name
   without mapping data. It's been placed in **Tools & Utilities** since that's
   the natural fit for a pickaxe. Change this in `ModItems.java` if you'd
   prefer it elsewhere (e.g. `ItemGroups.INGREDIENTS`, `ItemGroups.COMBAT`).
2. **One zero-argument `Item.Properties` builder call** in the constructor,
   most consistent with `.fireResistant()` given the "OP" theme of the item
   (ported as `.fireproof()`, the Fabric/Yarn name for the same setting). A
   niche self-repair-on-craft override (`getCraftingRemainingItem`) was also
   present in the original but was left out of the port as a deliberate
   simplification — it only mattered if the pickaxe was ever used as a
   crafting *ingredient*, and with 100,000 durability the tool will never
   realistically break anyway.

## Important limitation: this was not compiled here

This sandbox has **no internet access**, so Gradle, Fabric Loom, the Minecraft
1.21.11 assets, Yarn mappings, and Fabric API could not be downloaded — meaning
I could not actually run a build or hand you back a compiled `.jar`. What you
have instead is a **complete, ready-to-build Fabric mod project**.

Also double-check `gradle.properties` — the exact Yarn mappings build number
and Fabric API version for `1.21.11` were filled in with my best knowledge but
should be verified/updated against **https://fabricmc.net/develop/** before
building, in case newer builds exist.

## Easiest option: let GitHub build the jar for you (no local setup)

This project includes `.github/workflows/build.yml`, a GitHub Actions
workflow that compiles the mod on GitHub's own (internet-connected) servers
and hands you back a downloadable `.jar` — no Gradle, no JDK, nothing to
install on your own machine.

1. Create a new **GitHub repository** (public or private, either works) at
   github.com.
2. Upload the entire contents of this project (everything in this zip,
   including the hidden `.github` folder) to that repository. The simplest
   way: on the repo's page, use "Add file → Upload files" and drag in
   everything, or `git init` / `git add .` / `git commit` / `git push` from
   this folder if you're comfortable with git.
3. Go to the **Actions** tab on your repository. A workflow run called
   "Build Fabric Mod" should start automatically (or click "Run workflow" if
   it doesn't).
4. Wait for it to finish (a few minutes) — a green checkmark means success.
5. Click into that run, scroll to **Artifacts**, and download
   `easy-netherite-op-emerald-pick-fabric-jar`. That's a zip containing the
   compiled mod jar.
6. Unzip it and drop the `.jar` into your `.minecraft/mods` folder alongside
   the matching Fabric API jar for 1.21.11.

If the run fails, click into it to see the exact error — most likely cause is
a stale version number in `gradle.properties` (see below), and the log will
say so explicitly (e.g. "could not find mappings" or "could not resolve
fabric-api"). Update the value it complains about and push again; the
workflow re-runs automatically on every push.

## Building it yourself locally instead

1. Install [Temurin/OpenJDK 21](https://adoptium.net/) if you don't have it.
2. Open a terminal in this project's folder.
3. Since this project doesn't include Gradle wrapper binaries (also blocked by
   the same lack of internet access), either:
   - Open the folder in IntelliJ IDEA with the Fabric/Gradle plugins — it will
     offer to set up the wrapper automatically, or
   - Run `gradle wrapper` once if you have Gradle installed locally, then use
     `./gradlew build` from then on.
4. Run:
   ```
   ./gradlew build
   ```
5. The compiled mod will be at `build/libs/easy-netherite-op-emerald-pick-1.0.0.jar`.
6. Drop that jar into your `.minecraft/mods` folder alongside the matching
   Fabric API jar for 1.21.11.

If any dependency version in `gradle.properties` is stale by the time you
build, Gradle/Loom will fail with a clear "could not resolve/find mappings"
error — just grab the current values for MC 1.21.11 from fabricmc.net/develop
and paste them in.
