from PIL import Image, ImageDraw

def pad_image_to_1024x1024(image_path, output_path, corner_radius=0):
    # 讀入原始圖片 594x830
    original = Image.open(image_path).convert("RGBA")

    # 確認原始圖片尺寸
    orig_w, orig_h = original.size
    print(f"Original size: {orig_w}x{orig_h}")

    mask = Image.new("L", original.size, 0)
    draw = ImageDraw.Draw(mask)
    draw.rounded_rectangle(
        [(0, 0), original.size],
        radius=corner_radius,
        fill=255
    )

    # 將圓角遮罩套用到圖片 alpha channel
    original.putalpha(mask)

    # 設定 padding 與新圖大小
    pad_left = 211
    pad_top = 98
    target_width = 1024
    target_height = 1024

    # 計算新的圖片右下角位置
    new_w = pad_left + orig_w
    new_h = pad_top + orig_h

    # 確保圖片不會超出 1024x1024
    if new_w > target_width or new_h > target_height:
        raise ValueError("Padded image would exceed 1024x1024 dimensions.")

    # 建立透明背景的 1024x1024 圖片
    padded = Image.new("RGBA", (target_width, target_height), (0, 0, 0, 0))

    # 貼上原始圖片
    padded.paste(original, (pad_left, pad_top))

    # 儲存新圖
    padded.save(f"{output_path}_p.png", "PNG")
    print(f"Padded image saved to {output_path}")

    # 縮小為 512x512（並保留圓角透明）
    resized = padded.resize((512, 512), Image.LANCZOS)

    # 儲存 512x512
    resized.save(f"{output_path}.png", "PNG")


# 用法範例
pad_image_to_1024x1024("Strike.png", "Strike_s", 30) # attack 30
pad_image_to_1024x1024("Defend.png", "Defend_s", 40) # skill 40
# power 50
