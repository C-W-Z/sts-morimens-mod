from PIL import Image, ImageDraw

# 406x614 cut to 370x580 -> scale to 594x931 -> cut to 594x830

def pad_image_to_1024x1024(image_path, output_path, corner_radius=0, mask=False, frame_path="Card_Power_Frame.png"):
    # 讀入原始圖片 594x830
    original = Image.open(image_path).convert("RGBA")

    # 確認原始圖片尺寸
    orig_w, orig_h = original.size
    print(f"Original size: {orig_w}x{orig_h}")

    if not mask:
        mask = Image.new("L", original.size, 0)
        draw = ImageDraw.Draw(mask)
        draw.rounded_rectangle(
            [(0, 0), original.size],
            radius=corner_radius,
            fill=255
        )

        # 將圓角遮罩套用到圖片 alpha channel
        original.putalpha(mask)
    else:
        mask = Image.open("alpha_mask.png").convert("RGBA").getchannel("A")

        # original is from 406x614 -> cut to 392x610 -> scale to 594x924 -> cut to 594x830

        original.putalpha(mask)

        frame = Image.open(frame_path).convert("RGBA")
        original.paste(frame, (0,0), frame)

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
pad_image_to_1024x1024("Defend.png", "Defend_s", 30) # skill 40
pad_image_to_1024x1024("Inspiration.png", "Inspiration_s", 30) # skill 40

pad_image_to_1024x1024("Ramona.png", "Ramona_s", 0, True, "Card_Skill_Frame.png")
pad_image_to_1024x1024("HandOfOblivion.png", "HandOfOblivion_s", 30)